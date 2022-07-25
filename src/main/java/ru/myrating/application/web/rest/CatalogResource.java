package ru.myrating.application.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myrating.application.service.CatalogService;
import ru.myrating.application.service.dto.order.OrderStatusDTO;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;
import static ru.myrating.application.domain.enumeration.OrderStatusEnum.getOrderStatusForFront;
import static ru.myrating.application.security.AuthoritiesConstants.ADMIN;

@RestController
@RequestMapping("/api/catalog")
public class CatalogResource {
    private final CatalogService catalogService;

    public CatalogResource(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PreAuthorize("hasRole(\"" + ADMIN + "\")")
    @PostMapping("/clear-cache")
    public ResponseEntity<?> clearCache() {
        catalogService.clearCaches();
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/order-status")
    public ResponseEntity<List<OrderStatusDTO>> getOrderStatus() {
        return new ResponseEntity<>(getOrderStatusForFront().stream().map(OrderStatusDTO::new).collect(toList()), OK);
    }
}
