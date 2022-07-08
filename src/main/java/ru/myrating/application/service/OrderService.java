package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.repository.OrderRepository;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;
import ru.myrating.application.web.rest.errors.NotFoundAlertException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static ru.myrating.application.config.Constants.EMAIL_REGEX;
import static ru.myrating.application.domain.enumeration.OrderStatusEnum.NEW;
import static ru.myrating.application.domain.enumeration.OrderStatusEnum.PAID;

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
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundAlertException("Order not found", "OrderRequest", "notfound"));
    }

    public String updateStatusPaid(Long orderId, String transactionId) {
        try {
            OrderRequest orderRequest = getOne(orderId);
            if (!PAID.equals(orderRequest.getStatus())
                    && NEW.equals(orderRequest.getStatus())) {
                orderRequest.setStatus(PAID);
                orderRequest.setPaymentTransactionId(transactionId);
                save(orderRequest);
                ratingService.startCalculateRating(orderRequest);
                return "Link on report";
            }
        } catch (Exception e) {
            throw new BadRequestAlertException("Error call function", "OrderService", "error");
        }
        throw new BadRequestAlertException("Error call function", "OrderService", "error");
    }

    public OrderRequest createOrder(OrderRequest orderRequest) {
        validationRequest(orderRequest);
        orderRequest.setStatus(NEW);
        return save(orderRequest);
    }

    private void validationRequest(OrderRequest orderRequest) {
        if (isNotEmpty(orderRequest.getOrderData().getFirstName())) {
            if (!String.valueOf(orderRequest.getOrderData().getFirstName().toUpperCase().charAt(0)).matches("[А-Я]")) {
                throw new BadRequestAlertException("FirstName must contains only Cyrillic upper letters", "OrderService", "validationrequest");
            }
        } else {
            throw new BadRequestAlertException("FirstName must not be empty", "OrderService", "validationrequest");
        }
        if (isNotEmpty(orderRequest.getOrderData().getLastName())) {
            if (!String.valueOf(orderRequest.getOrderData().getLastName().toUpperCase().charAt(0)).matches("[А-Я]")) {
                throw new BadRequestAlertException("LastName must contains only Cyrillic upper letters", "OrderService", "validationrequest");
            }
        } else {
            throw new BadRequestAlertException("LastName must not be empty", "OrderService", "validationrequest");
        }
        if (orderRequest.getOrderData().getPassportSerial() != null) {
            if (!String.valueOf(orderRequest.getOrderData().getPassportSerial()).matches("\\d{4}")) {
                throw new BadRequestAlertException("PassportSerial must contains only digital and length 4", "OrderService", "validationrequest");
            }
        } else {
            throw new BadRequestAlertException("PassportSerial must not be empty", "OrderService", "validationrequest");
        }
        if (orderRequest.getOrderData().getPassportNumber() != null) {
            if (!String.valueOf(orderRequest.getOrderData().getPassportNumber()).matches("\\d{6}"))
                throw new BadRequestAlertException("PassportNumber must contains only digital and length 6", "OrderService", "validationrequest");
        } else {
            throw new BadRequestAlertException("PassportNumber must not be empty", "OrderService", "validationrequest");
        }
        if (isNotEmpty(orderRequest.getOrderData().getEmail())) {
            if (!orderRequest.getOrderData().getEmail().toUpperCase().matches(EMAIL_REGEX)) {
                throw new BadRequestAlertException("Email invalid", "OrderService", "validationrequest");
            }
        } else {
            throw new BadRequestAlertException("Email must not be empty", "OrderService", "validationrequest");
        }
        if (orderRequest.getOrderData().getBirthDate() == null) {
            throw new BadRequestAlertException("BirthDate must not be empty", "OrderService", "validationrequest");
        }
    }
}
