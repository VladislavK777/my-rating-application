package ru.myrating.application.web.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myrating.application.security.AuthoritiesConstants;
import ru.myrating.application.service.OrderService;
import ru.myrating.application.service.dto.OrderRequestDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/order")
public class OrderResource {
    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Page<OrderRequestDto>> getAllOrders(Pageable pageable) {
        return new ResponseEntity<>(orderService.getAllOrders(pageable), OK);
    }
}