package ru.myrating.application.web.rest;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myrating.application.domain.enumeration.OrderStatusEnum;
import ru.myrating.application.security.AuthoritiesConstants;
import ru.myrating.application.service.ReportService;
import ru.myrating.application.service.dto.criteria.UserCriteria;

import java.io.IOException;

import static org.springframework.http.ContentDisposition.parse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.parseMediaType;

@RestController
@RequestMapping("/api/report")
public class ReportResource {
    private final ReportService reportService;

    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ByteArrayResource> getAllUsers(UserCriteria criteria) throws IOException {
        return new ResponseEntity<>(reportService.getAllUsers(criteria), getHeaders("users"), CREATED);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ByteArrayResource> getAllOrders() throws IOException {
        return new ResponseEntity<>(reportService.getAllOrders(), getHeaders("orders"), CREATED);
    }

    @GetMapping("/orders/partner")
    public ResponseEntity<ByteArrayResource> getOrdersByPartner(@RequestParam(required = false) String dateFrom,
                                                                @RequestParam(required = false) String dateTo,
                                                                @RequestParam(required = false) OrderStatusEnum status) throws IOException {

        return new ResponseEntity<>(reportService.getOrdersByPartner(dateFrom, dateTo, status), getHeaders("orders"), CREATED);
    }

    private HttpHeaders getHeaders(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(parse("attachment; filename=" + name + ".csv"));
        headers.setContentType(parseMediaType("application/vnd.ms-excel"));
        return headers;
    }
}
