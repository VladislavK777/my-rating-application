package ru.myrating.application.web.rest;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.service.OrderService;
import ru.myrating.application.service.ValidationService;
import ru.myrating.application.service.dto.OrderRequestDto;
import ru.myrating.application.web.rest.errors.AccessDeniedAlertException;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RateLimiter(name = "orderResource")
@RestController
@RequestMapping("/public/api/order")
public class OrderResource {
    private final OrderService orderService;
    private final ValidationService validationService;

    public OrderResource(OrderService orderService, ValidationService validationService) {
        this.orderService = orderService;
        this.validationService = validationService;
    }

    @PostMapping
    public OrderRequestDto create(@RequestHeader(value = "X-API-Key", required = false) String key,
                                  @RequestBody OrderRequest body) {
        if (validationService.validationApiKey(key)) {
            return new OrderRequestDto(orderService.createOrder(body));
        }
        throw new AccessDeniedAlertException("Access denied!", "Validation", "validationerror");
    }

    @PutMapping("/paid/{id}")
    public ResponseEntity<?> updatePaid(@RequestHeader(value = "X-API-Key", required = false) String key,
                                        @PathVariable Long id,
                                        @RequestParam String transactionId) {
        if (validationService.validationApiKey(key)) {
            orderService.updateStatusPaid(id, transactionId);
            return new ResponseEntity<>(OK);
        }
        throw new AccessDeniedAlertException("Access denied!", "Validation", "validationerror");
    }

    @GetMapping("/result/{linkId}")
    public ResponseEntity<Map<String, Object>> getResult(@RequestHeader(value = "X-API-Key", required = false) String key,
                                                         @PathVariable String linkId) {
        if (validationService.validationApiKey(key)) {
            return new ResponseEntity<>(orderService.getResultMapOrder(linkId), OK);
        }
        throw new AccessDeniedAlertException("Access denied!", "Validation", "validationerror");
    }
}
