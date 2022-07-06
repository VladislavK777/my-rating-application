package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.domain.enumeration.StatusOrderEnum;
import ru.myrating.application.integration.IntegrationProviderService;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;

import java.util.Map;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
@Transactional
public class RatingService {
    private final Logger log = LoggerFactory.getLogger(RatingService.class);
    private final CalculateService calculateService;
    private final IntegrationProviderService integrationProviderService;

    public RatingService(CalculateService calculateService, IntegrationProviderService integrationProviderService) {
        this.calculateService = calculateService;
        this.integrationProviderService = integrationProviderService;
    }

    public OrderRequest startCalculateRating(OrderRequest orderRequest) {
        try {
            OrderResponse orderResponse = integrationProviderService.callOutService(orderRequest);
            Map<String, Object> ratingModel = calculateService.calculateRatingModel(orderResponse);
            System.out.println(ratingModel);
            OrderRequest orderRequest1 = orderResponse.getOrderRequest();
            orderRequest1.setStatus(StatusOrderEnum.PAID);
            return orderRequest1;
        } catch (Exception e) {
            log.error("Calculate rating failed: {}", e);
            throw new BadRequestAlertException("Calculate rating failed", ENTITY_NAME, "calculationfailed");
        }
    }
}
