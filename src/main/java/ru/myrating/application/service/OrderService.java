package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.enumeration.StatusOrderEnum;
import ru.myrating.application.repository.OrderRepository;
import ru.myrating.application.service.dto.OrderRequestDto;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
@Transactional
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final RatingService ratingService;

    public OrderService(OrderRepository orderRepository, RatingService ratingService) {
        this.orderRepository = orderRepository;
        this.ratingService = ratingService;
    }

    public OrderRequest save(OrderRequest orderRequest) {
        return orderRepository.saveAndFlush(orderRequest);
    }

    public OrderRequest getOne(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new BadRequestAlertException("Order not found", ENTITY_NAME, "notfound"));
    }

    public OrderRequest createOrder(OrderRequest orderRequest) {
        if (!String.valueOf(orderRequest.getOrderData().getFirstName().charAt(0)).matches("[А-Я]"))
            throw new BadRequestAlertException("Only lowercase [А-Я]", ENTITY_NAME, "lowercase");
        if (!String.valueOf(orderRequest.getOrderData().getLastName().charAt(0)).matches("[А-Я]"))
            throw new BadRequestAlertException("Only lowercase [А-Я]", ENTITY_NAME, "lowercase");
        orderRequest.setStatus(StatusOrderEnum.NEW);
        save(orderRequest);
        return save(ratingService.startCalculateRating(orderRequest));
    }
}
