package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.myrating.application.config.ApplicationProperties;
import ru.myrating.application.domain.OrderFaultQueue;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.integration.IntegrationProviderService;
import ru.myrating.application.out.ProductType;
import ru.myrating.application.service.mapper.OrderResponseMapper;
import ru.myrating.application.web.rest.errors.InternalServerErrorAlertException;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static ru.myrating.application.domain.enumeration.OrderStatusEnum.CALCULATED;
import static ru.myrating.application.domain.enumeration.OrderStatusEnum.FAULT;

@Async("taskExecutor")
@Service
public class RatingService {
    private final Logger log = LoggerFactory.getLogger(RatingService.class);
    private final ApplicationProperties applicationProperties;
    private final CalculateService calculateService;
    private final IntegrationProviderService integrationProviderService;
    private final OrderResponseService orderResponseService;
    private final OrderFaultService orderFaultService;
    private final OrderResponseMapper orderResponseMapper;
    @Lazy
    private final OrderService orderService;

    public RatingService(ApplicationProperties applicationProperties, CalculateService calculateService, IntegrationProviderService integrationProviderService, OrderResponseService orderResponseService, OrderFaultService orderFaultService, OrderResponseMapper orderResponseMapper, @Lazy OrderService orderService) {
        this.applicationProperties = applicationProperties;
        this.calculateService = calculateService;
        this.integrationProviderService = integrationProviderService;
        this.orderResponseService = orderResponseService;
        this.orderFaultService = orderFaultService;
        this.orderResponseMapper = orderResponseMapper;
        this.orderService = orderService;
    }

    public void startCalculateRating(OrderRequest orderRequest) {
        try {
            ProductType productType;
            try {
                productType = integrationProviderService.callOutService(orderRequest);
            } catch (Exception e) {
                log.error("Get fault from integration service: " + e);
                saveError(orderRequest, e.getMessage());
                throw new InternalServerErrorAlertException("Get fault from integration service; cause: " + e.getMessage(), "RatingService", "integrationfailed");
            }
            OrderResponse orderResponse = orderResponseMapper.dtoToDao(productType.getPreply().getPcr().getReasons());
            orderResponse.setOrderRequest(orderRequest);
            orderResponseService.save(orderResponse);
            Map<String, Object> map = calculateService.calculateRatingModel(orderResponse);
            orderRequest.setStatus(CALCULATED);
            orderRequest.setOrderResult(map);
            orderService.save(orderRequest);
        } catch (Exception e) {
            log.error("Calculate rating failed: " + e);
            throw new InternalServerErrorAlertException("Calculate rating failed; cause: " + e.getMessage(), "RatingService", "calculationfailed");
        }
    }

    private void saveError(OrderRequest orderRequest, String message) {
        OrderFaultQueue orderFaultQueue = orderFaultService.getByOrderRequest(orderRequest);
        if (orderFaultQueue == null) {
            orderFaultQueue = new OrderFaultQueue(orderRequest, now().plusDays(applicationProperties.getDurationFaultAttempts().getDays()), message);
        } else {
            if (!message.equals(orderFaultQueue.getMessageError()))
                orderFaultQueue.setMessageError(message);
        }
        orderFaultService.save(orderFaultQueue);
        orderRequest.setStatus(FAULT);
        orderService.save(orderRequest);
    }
}
