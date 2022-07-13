package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.myrating.application.config.ApplicationProperties;
import ru.myrating.application.domain.OrderFaultQueue;
import ru.myrating.application.domain.OrderReportContent;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.integration.IntegrationProviderService;
import ru.myrating.application.out.ProductType;
import ru.myrating.application.service.mapper.OrderResponseMapper;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;
import ru.myrating.application.web.rest.errors.InternalServerErrorAlertException;
import ru.myrating.application.web.websocket.dto.ReportDTO;
import tech.jhipster.config.JHipsterConstants;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static ru.myrating.application.config.Constants.WEBSOCKET_QUEUE;
import static ru.myrating.application.domain.enumeration.OrderStatusEnum.*;

@Async("taskExecutor")
@Service
public class RatingService {
    private final Logger log = LoggerFactory.getLogger(RatingService.class);
    private final Environment env;
    private final ApplicationProperties applicationProperties;
    private final CalculateService calculateService;
    private final IntegrationProviderService integrationProviderService;
    private final OrderResponseService orderResponseService;
    private final OrderFaultService orderFaultService;
    private final OrderResponseMapper orderResponseMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MailService mailService;
    @Lazy
    private final OrderService orderService;

    public RatingService(Environment env, ApplicationProperties applicationProperties, CalculateService calculateService, IntegrationProviderService integrationProviderService, OrderResponseService orderResponseService, OrderFaultService orderFaultService, OrderResponseMapper orderResponseMapper, SimpMessagingTemplate simpMessagingTemplate, MailService mailService, @Lazy OrderService orderService) {
        this.env = env;
        this.applicationProperties = applicationProperties;
        this.calculateService = calculateService;
        this.integrationProviderService = integrationProviderService;
        this.orderResponseService = orderResponseService;
        this.orderFaultService = orderFaultService;
        this.orderResponseMapper = orderResponseMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.mailService = mailService;
        this.orderService = orderService;
    }

    //  ------ ДЛЯ ДЕМО ------ //
    private final Map<String, OrderResponse> mapResponse = new HashMap<>();

    @PostConstruct
    public void init() {
        mapResponse.put("0", new OrderResponse(2L, 2L, 0L, 9L, 1L, 1L, 8L, 16L, 0L, 0L, 0L, 1L, 1L, 0L, 0L, 0L, 0L, 1L, 0L, 0L, 0L, "0", 1L, "1", 0L));
        mapResponse.put("1", new OrderResponse(0L, 0L, 0L, 98L, 2L, 1L, 7L, 15L, 0L, 0L, 0L, 1L, 1L, 1L, 1L, 0L, 0L, 0L, 1L, 0L, 0L, "5", 9L, "5", 1L));
        mapResponse.put("2", new OrderResponse(0L, 0L, 0L, 137L, 5L, 1L, 9L, 16L, 0L, 0L, 1L, 1L, 1L, 0L, 0L, 0L, 0L, 1L, 1L, 0L, 0L, "2", 6L, "2", 0L));
        mapResponse.put("3", new OrderResponse(0L, 0L, 0L, 137L, 5L, 1L, 9L, 16L, 0L, 0L, 1L, 1L, 1L, 0L, 0L, 0L, 0L, 1L, 1L, 0L, 0L, "2", 6L, "2", 0L));
        mapResponse.put("4", new OrderResponse(0L, 0L, 0L, 1L, 1L, 1L, 1L, 1L, 0L, 0L, 0L, 0L, 0L, 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, "0", 1L, "4", 0L));
        mapResponse.put("5", new OrderResponse(0L, 0L, 1L, 46L, 3L, 1L, 5L, 12L, 0L, 0L, 0L, 1L, 1L, 1L, 1L, 0L, 0L, 1L, 1L, 0L, 0L, "5", 7L, "5", 1L));
        mapResponse.put("6", new OrderResponse(0L, 0L, 0L, 77L, 1L, 1L, 5L, 2L, 0L, 0L, 0L, 0L, 1L, 0L, 0L, 0L, 0L, 1L, 0L, 0L, 0L, "1", 1L, "5", 0L));
        mapResponse.put("7", new OrderResponse(0L, 0L, 0L, 7L, 1L, 1L, 1L, 2L, 0L, 0L, 0L, 0L, 0L, 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, "5", 4L, "5", 0L));
        mapResponse.put("8", new OrderResponse(0L, 0L, 0L, 73L, 2L, 1L, 7L, 14L, 0L, 0L, 0L, 1L, 1L, 0L, 0L, 0L, 0L, 1L, 0L, 1L, 1L, "A", 1L, "5", 0L));
        mapResponse.put("9", new OrderResponse(1L, 1L, 2L, 145L, 3L, 1L, 11L, 16L, 0L, 1L, 1L, 1L, 1L, 0L, 0L, 0L, 1L, 1L, 1L, 0L, 0L, "A", 4L, "A", 0L));
    }

    public void calculateRating(OrderRequest orderRequest) {
        try {
            OrderResponse orderResponse = null;
            if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_PRODUCTION))) {
                ProductType productType;
                try {
                    productType = integrationProviderService.callOutService(orderRequest);
                } catch (Exception e) {
                    log.error("Get fault from integration service: " + e);
                    saveError(orderRequest, e.getMessage());
                    throw new InternalServerErrorAlertException("Get fault from integration service; cause: " + e.getMessage(), "RatingService", "integrationfailed");
                }
                orderResponse = orderResponseMapper.dtoToDao(productType.getPreply().getPcr().getReasons());
            }

            //  ------ DEV ------ //
            if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT))) {
                int id = (int) (Math.random() * 9);
                orderResponse = mapResponse.get(String.valueOf(id));
            }
            if (orderResponse == null)
                throw new BadRequestAlertException("OrderResponse is null", "RatingService", "ordrerresponsenull");

            orderResponse.setOrderRequest(orderRequest);
            orderResponseService.save(orderResponse);
            Map<String, Object> map = calculateService.calculateRatingModel(orderResponse);
            UUID linkId = randomUUID();
            orderRequest.setOrderReportContent(new OrderReportContent(linkId, map, true, now().plusDays(applicationProperties.getLifeTimeResultDays())));
            simpMessagingTemplate.convertAndSend(WEBSOCKET_QUEUE, new ReportDTO(orderRequest.getId(), linkId.toString()));
            mailService.sendEmail(orderRequest.getOrderData().getEmail(), "Ваш отчет по рейтингу готов", applicationProperties.getLinkReport() + linkId, false, false);
            orderRequest.setStatus(SENT);
            orderService.save(orderRequest);
        } catch (Exception e) {
            log.error("Calculate rating failed: " + e);
            saveError(orderRequest, e.getMessage());
            throw new InternalServerErrorAlertException("Calculate rating failed; cause: " + e.getMessage(), "RatingService", "calculationfailed");
        }
    }

    private void saveError(OrderRequest orderRequest, String message) {
        OrderFaultQueue orderFaultQueue = orderFaultService.getByOrderRequest(orderRequest, now());
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
