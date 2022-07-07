package ru.myrating.application.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.myrating.application.domain.OrderFaultQueue;
import ru.myrating.application.service.OrderFaultService;
import ru.myrating.application.service.RatingService;

import java.util.List;

import static java.time.LocalDateTime.now;

@Component
public class RatingServiceScheduler {
    private final Logger log = LoggerFactory.getLogger(RatingServiceScheduler.class);
    private final OrderFaultService orderFaultService;
    @Lazy
    private final RatingService ratingService;

    public RatingServiceScheduler(OrderFaultService orderFaultService, @Lazy RatingService ratingService) {
        this.orderFaultService = orderFaultService;
        this.ratingService = ratingService;
    }

    @Scheduled(cron = "${application.duration-fault-attempts.cron}")
    public void retryFaultRequests() {
        log.info("Start scheduler retry fault order");
        List<OrderFaultQueue> list = orderFaultService.getActiveElements(now());
        if (!list.isEmpty()) {
            list.forEach(orderFault -> ratingService.startCalculateRating(orderFault.getOrderRequest()));
        }
    }
}
