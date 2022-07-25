package ru.myrating.application.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myrating.application.domain.enumeration.OrderStatusEnum;
import ru.myrating.application.security.AuthoritiesConstants;
import ru.myrating.application.service.OrderService;
import ru.myrating.application.service.dto.order.OrderRequestCounterDTO;
import ru.myrating.application.service.dto.order.OrderRequestDTO;

import java.util.List;

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
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), OK);
    }

    @GetMapping("/partner")
    public ResponseEntity<OrderRequestCounterDTO> getOrdersByPartner(@RequestParam(required = false) String dateFrom,
                                                                     @RequestParam(required = false) String dateTo,
                                                                     @RequestParam(required = false) OrderStatusEnum status) {
        return new ResponseEntity<>(orderService.getOrdersByPartner(dateFrom, dateTo, status), OK);
    }
}