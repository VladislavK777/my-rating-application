package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderReportContent;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.enumeration.OrderStatusEnum;
import ru.myrating.application.repository.OrderRepository;
import ru.myrating.application.security.SecurityUtils;
import ru.myrating.application.service.dto.criteria.UserCriteria;
import ru.myrating.application.service.dto.order.OrderRequestCounterDTO;
import ru.myrating.application.service.dto.order.OrderRequestDTO;
import ru.myrating.application.service.mapper.OrderRequestMapper;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;
import ru.myrating.application.web.rest.errors.NotFoundAlertException;
import tech.jhipster.service.filter.StringFilter;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;
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
            userQueryService.findUserByCriteria(criteria).ifPresent(orderRequest::setPartnerUser);
        }
        orderRequest.setStatus(NEW);
        return save(orderRequest);
    }

    public List<OrderRequestDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderRequestMapper::toDto).collect(toList());
    }

    public OrderRequestCounterDTO getOrdersByPartner(@Nullable String dateFrom, @Nullable String dateTo, @Nullable OrderStatusEnum status) {
        OrderRequestCounterDTO orderRequestCounterDTO = new OrderRequestCounterDTO();
        Optional<String> loginOptional = SecurityUtils.getCurrentUserLogin();
        if (loginOptional.isEmpty())
            throw new BadRequestAlertException("Login is empty", "orderManagement", "notfound");
        String login = loginOptional.get();
        List<OrderRequest> orderRequests = orderRepository.findAllByPartnerUser(login);
        Integer all = orderRequests.size();
        Integer allPaid = (int) orderRequests.stream().filter(orderRequest -> PAID.equals(orderRequest.getStatus())).count();
        orderRequestCounterDTO.setAll(all);
        orderRequestCounterDTO.setAllPaid(allPaid);
        if (isNotEmpty(dateFrom) && isNotEmpty(dateTo)) {
            LocalDateTime from = LocalDateTime.of(parse(dateFrom, ofPattern("yyyy-MM-dd")), LocalTime.of(0, 0, 0, 0));
            LocalDateTime to = LocalDateTime.of(parse(dateTo, ofPattern("yyyy-MM-dd")), LocalTime.of(23, 59, 59, 999999999));
            List<OrderRequest> orderRequestsPeriod = orderRepository.findAllByCreatedDateAndPartnerUser(from, to, login);
            orderRequestCounterDTO.setAllPeriod(orderRequestsPeriod.size());
            orderRequestCounterDTO.setAllPeriodPaid((int) orderRequestsPeriod.stream().filter(orderRequest -> PAID.equals(orderRequest.getStatus())).count());
            orderRequestCounterDTO.setOrders(status == null ? orderRequestsPeriod.stream()
                    .map(orderRequestMapper::toDtoByPartner).collect(toList())
                    : orderRequestsPeriod.stream().filter(orderRequest -> status.equals(orderRequest.getStatus()))
                    .map(orderRequestMapper::toDtoByPartner).collect(toList()));
        } else {
            orderRequestCounterDTO.setAllPeriod(all);
            orderRequestCounterDTO.setAllPeriodPaid(allPaid);
            orderRequestCounterDTO.setOrders(status == null ? orderRequests.stream()
                    .map(orderRequestMapper::toDtoByPartner).collect(toList())
                    : orderRequests.stream().filter(orderRequest -> status.equals(orderRequest.getStatus()))
                    .map(orderRequestMapper::toDtoByPartner).collect(toList()));
        }
        return orderRequestCounterDTO;
    }
}
