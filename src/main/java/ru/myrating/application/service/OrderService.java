package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderReportContent;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.repository.OrderRepository;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;
import ru.myrating.application.web.rest.errors.NotFoundAlertException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.myrating.application.domain.enumeration.OrderStatusEnum.NEW;
import static ru.myrating.application.domain.enumeration.OrderStatusEnum.PAID;
import static ru.myrating.application.web.rest.errors.ErrorConstants.ERR_VALIDATION;

@Service
@Transactional
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderReportContentService orderReportContentService;
    private final RatingService ratingService;

    public OrderService(OrderRepository orderRepository, OrderReportContentService orderReportContentService, RatingService ratingService) {
        this.orderRepository = orderRepository;
        this.orderReportContentService = orderReportContentService;
        this.ratingService = ratingService;
    }

    public OrderRequest save(OrderRequest orderRequest) {
        return orderRepository.saveAndFlush(orderRequest);
    }

    public OrderRequest getOne(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundAlertException("Order not found", "orderManagement", "notfound"));
    }

    public Map<String, Object> getResultMapOrder(String linkId) {
        OrderReportContent orderReportContent = orderReportContentService.findByOrderResultLink(linkId);
        if (orderReportContent.isActivated())
            return orderReportContent.getOrderResult();
        throw new NotFoundAlertException("Order result not found", "orderManagement", "notfound");
    }

    public void updateStatusPaid(Long orderId, String transactionId) {
        try {
            OrderRequest orderRequest = getOne(orderId);
            if (!PAID.equals(orderRequest.getStatus())
                    && NEW.equals(orderRequest.getStatus())) {
                orderRequest.setStatus(PAID);
                orderRequest.setPaymentTransactionId(transactionId);
                save(orderRequest);
                ratingService.calculateRating(orderRequest);
            }
        } catch (Exception e) {
            throw new BadRequestAlertException("Error call function", "orderManagement", ERR_VALIDATION);
        }
    }

    public List<OrderRequest> findAllByCreatedDateBeforeAndPersonDataIsDeletedFalse(LocalDateTime dateNow) {
        return orderRepository.findAllByCreatedDateBeforeAndPersonDataIsDeletedFalse(dateNow);
    }

    public void deletePersonData(OrderRequest orderRequest) {
        orderRequest.getOrderData().setPassportNumber(null);
        orderRequest.getOrderData().setPassportSerial(null);
        orderRequest.setPersonDataIsDeleted(true);
        save(orderRequest);
    }

    public OrderRequest createOrder(OrderRequest orderRequest) {
        orderRequest.setStatus(NEW);
        return save(orderRequest);
    }
}
