package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.repository.OrderResponseRepository;
import ru.myrating.application.web.rest.errors.NotFoundAlertException;

@Service
@Transactional
public class OrderResponseService {
    private final Logger log = LoggerFactory.getLogger(OrderResponseService.class);

    private final OrderResponseRepository orderResponseRepository;

    public OrderResponseService(OrderResponseRepository orderResponseRepository) {
        this.orderResponseRepository = orderResponseRepository;
    }

    public OrderResponse save(OrderResponse orderResponse) {
        return orderResponseRepository.saveAndFlush(orderResponse);
    }

    public OrderResponse getOne(Long id) {
        return orderResponseRepository.findById(id).orElseThrow(() -> new NotFoundAlertException("Data not found", "OrderResponse", "notfound"));
    }
}
