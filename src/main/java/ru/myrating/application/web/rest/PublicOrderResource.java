package ru.myrating.application.web.rest;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.service.OrderService;
import ru.myrating.application.service.ValidationService;
import ru.myrating.application.service.dto.OrderRequestDto;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RateLimiter(name = "orderResource")
@RestController
@RequestMapping("/public/api/order")
public class PublicOrderResource {
    private final OrderService orderService;
    private final ValidationService validationService;

    public PublicOrderResource(OrderService orderService, ValidationService validationService) {
        this.orderService = orderService;
        this.validationService = validationService;
    }

    @PostMapping
    public ResponseEntity<OrderRequestDto> create(@RequestHeader(value = "X-API-Key", required = false) String key,
                                                  @RequestParam(required = false) String referenceLink,
                                                  @RequestBody OrderRequest body) {
        validationService.validationApiKey(key);
        validationService.validationRequest(body);
        return new ResponseEntity<>(new OrderRequestDto(orderService.createOrder(body, referenceLink).getId()), OK);
    }

    @PutMapping("/paid/{id}")
    public ResponseEntity<?> updatePaid(@RequestHeader(value = "X-API-Key", required = false) String key,
                                        @PathVariable Long id,
                                        @RequestParam String transactionId) {
        validationService.validationApiKey(key);
        orderService.updateStatusPaid(id, transactionId);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/result/{linkId}")
    public ResponseEntity<Map<String, Object>> getResult(@RequestHeader(value = "X-API-Key", required = false) String key,
                                                         @PathVariable String linkId) {
        validationService.validationApiKey(key);
        return new ResponseEntity<>(orderService.getResultMapOrder(linkId), OK);
    }
}
