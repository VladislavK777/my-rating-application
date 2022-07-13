package ru.myrating.application.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.myrating.application.config.ApplicationProperties;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.service.OrderService;

import java.util.List;

import static java.time.LocalDateTime.now;

@Component
public class OrderServiceScheduler {
    private final Logger log = LoggerFactory.getLogger(OrderServiceScheduler.class);
    private final ApplicationProperties applicationProperties;
    private final OrderService orderService;

    public OrderServiceScheduler(ApplicationProperties applicationProperties, OrderService orderService) {
        this.applicationProperties = applicationProperties;
        this.orderService = orderService;
    }

    @Scheduled(cron = "${application.person-data-delete-cron}")
    public void deletePersonDataInRequests() {
        log.info("Start scheduler delete person data order");
        List<OrderRequest> list = orderService.findAllByCreatedDateBeforeAndPersonDataIsDeletedFalse(now().minusDays(applicationProperties.getPersonDataDeleteDays()));
        if (!list.isEmpty()) {
            list.forEach(orderService::deletePersonData);
        }
    }
}
