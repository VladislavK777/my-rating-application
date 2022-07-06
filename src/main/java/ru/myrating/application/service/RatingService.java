package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.OrderResponse;
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

    public Map<String, Object> startCalculateRating(OrderRequest orderRequest) {
        try {
            OrderResponse orderResponse = integrationProviderService.callOutService(orderRequest);
            return calculateService.calculateRatingModel(orderResponse);
        } catch (Exception e) {
            log.error("Calculate rating failed: " + e);
            throw new BadRequestAlertException("Calculate rating failed; cause: " + e.getMessage(), ENTITY_NAME, "calculationfailed");
        }
    }
}
