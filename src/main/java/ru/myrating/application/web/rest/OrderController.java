package ru.myrating.application.web.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.service.OrderService;
import ru.myrating.application.service.dto.OrderRequestDto;

import java.security.Principal;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderRequestDto create(@RequestBody OrderRequest body, Principal principal) {
        UserDetails currentUser
                = (UserDetails) ((Authentication) principal).getPrincipal();
        System.out.println(currentUser.getUsername());
        return new OrderRequestDto(orderService.createOrder(body));
    }
}
