package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderReportContent;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.repository.OrderRepository;
import ru.myrating.application.service.dto.OrderRequestDto;
import ru.myrating.application.service.dto.UserCriteria;
import ru.myrating.application.service.mapper.OrderRequestMapper;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;
import ru.myrating.application.web.rest.errors.NotFoundAlertException;
import tech.jhipster.service.filter.StringFilter;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
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
    private final UserQueryService userQueryService;
    private final OrderRequestMapper orderRequestMapper;

    public OrderService(OrderRepository orderRepository,
                        OrderReportContentService orderReportContentService,
                        RatingService ratingService,
                        UserQueryService userQueryService,
                        OrderRequestMapper orderRequestMapper
    ) {
        this.orderRepository = orderRepository;
        this.orderReportContentService = orderReportContentService;
        this.ratingService = ratingService;
        this.userQueryService = userQueryService;
        this.orderRequestMapper = orderRequestMapper;
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

    public OrderRequest createOrder(OrderRequest orderRequest, @Nullable String referenceLink) {
        if (isNotEmpty(referenceLink)) {
            log.info("Order are creating with refLink: {}", referenceLink);
            UserCriteria criteria = new UserCriteria();
            StringFilter referenceLinkFilter = new StringFilter();
            referenceLinkFilter.setEquals(referenceLink);
            criteria.setReferenceLink(referenceLinkFilter);
            userQueryService.findUserByReferenceLink(criteria).ifPresent(orderRequest::setPartnerUser);
        }
        orderRequest.setStatus(NEW);
        return save(orderRequest);
    }

    public Page<OrderRequestDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderRequestMapper::toDto);
    }
}
