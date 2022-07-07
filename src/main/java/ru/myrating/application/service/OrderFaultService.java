package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.myrating.application.domain.OrderFaultQueue;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.repository.OrderFaultQueueRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderFaultService {
    private final Logger log = LoggerFactory.getLogger(OrderFaultService.class);

    private final OrderFaultQueueRepository orderFaultQueueRepository;

    public OrderFaultService(OrderFaultQueueRepository orderFaultQueueRepository) {
        this.orderFaultQueueRepository = orderFaultQueueRepository;
    }

    public OrderFaultQueue getByOrderRequest(OrderRequest orderRequest) {
        return orderFaultQueueRepository.findByOrderRequest(orderRequest).orElse(null);
    }

    public OrderFaultQueue save(OrderFaultQueue orderFaultQueue) {
        return orderFaultQueueRepository.saveAndFlush(orderFaultQueue);
    }

    public OrderFaultQueue update(OrderFaultQueue orderFaultQueue) {
        return save(orderFaultQueue);
    }

    public List<OrderFaultQueue> getActiveElements(LocalDateTime now) {
        return orderFaultQueueRepository.findAllByDeadLineDateAfter(now);
    }
}
