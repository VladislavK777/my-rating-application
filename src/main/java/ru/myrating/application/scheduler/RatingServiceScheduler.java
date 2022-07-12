package ru.myrating.application.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.myrating.application.domain.OrderFaultQueue;
import ru.myrating.application.domain.OrderReportContent;
import ru.myrating.application.service.OrderFaultService;
import ru.myrating.application.service.OrderReportContentService;
import ru.myrating.application.service.RatingService;

import java.util.List;

import static java.time.LocalDateTime.now;

@Component
public class RatingServiceScheduler {
    private final Logger log = LoggerFactory.getLogger(RatingServiceScheduler.class);
    private final OrderFaultService orderFaultService;
    private final OrderReportContentService orderReportContentService;
    private final RatingService ratingService;

    public RatingServiceScheduler(OrderFaultService orderFaultService, OrderReportContentService orderReportContentService, RatingService ratingService) {
        this.orderFaultService = orderFaultService;
        this.orderReportContentService = orderReportContentService;
        this.ratingService = ratingService;
    }

    @Scheduled(cron = "${application.duration-fault-attempts.cron}")
    public void retryFaultRequests() {
        log.info("Start scheduler retry fault order");
        List<OrderFaultQueue> list = orderFaultService.getActiveElements(now());
        if (!list.isEmpty()) {
            list.forEach(orderFault -> ratingService.calculateRating(orderFault.getOrderRequest()));
        }
    }

    @Scheduled(cron = "${application.deactivate-result-crone}")
    public void deactivateResultRequests() {
        log.info("Start scheduler deactivate result order");
        List<OrderReportContent> list = orderReportContentService.findAllByDeactivateDateAfter(now());
        if (!list.isEmpty()) {
            list.forEach(content -> {
                orderReportContentService.deactivateContent(content);
                ratingService.deletePersonDateByOrder(content.getOrderRequest());
            });
        }
    }
}
