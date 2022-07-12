package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderReportContent;
import ru.myrating.application.repository.OrderReportContentRepository;
import ru.myrating.application.web.rest.errors.NotFoundAlertException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.fromString;

@Service
@Transactional
public class OrderReportContentService {
    private final Logger log = LoggerFactory.getLogger(OrderReportContentService.class);

    private final OrderReportContentRepository orderReportContentRepository;

    public OrderReportContentService(OrderReportContentRepository orderReportContentRepository) {
        this.orderReportContentRepository = orderReportContentRepository;
    }

    public OrderReportContent save(OrderReportContent orderReportContent) {
        return orderReportContentRepository.saveAndFlush(orderReportContent);
    }

    public OrderReportContent getOne(Long id) {
        return orderReportContentRepository.findById(id).orElseThrow(() -> new NotFoundAlertException("OrderResult not found", "OrderReportContentService", "notfound"));
    }

    public OrderReportContent findByOrderResultLink(String link) {
        return orderReportContentRepository.findByOrderResultLink(fromString(link)).orElseThrow(() -> new NotFoundAlertException("OrderResult not found", "OrderReportContentService", "notfound"));
    }

    public List<OrderReportContent> findAllByDeactivateDateAfter(LocalDateTime dateNow) {
        return orderReportContentRepository.findAllByDeactivateDateAfter(dateNow);
    }

    public void deactivateContent(OrderReportContent orderReportContent) {
        orderReportContent.setActivated(false);
        save(orderReportContent);
    }
}
