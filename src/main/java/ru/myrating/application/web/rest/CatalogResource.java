package ru.myrating.application.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myrating.application.service.CatalogService;

import static org.springframework.http.HttpStatus.OK;
import static ru.myrating.application.security.AuthoritiesConstants.ADMIN;

@RestController
@RequestMapping("/api/admin")
public class CatalogResource {
    private final CatalogService catalogService;

    public CatalogResource(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PreAuthorize("hasRole(\"" + ADMIN + "\")")
    @PostMapping("/catalog/clear-cache")
    public ResponseEntity<?> clearCache() {
        catalogService.clearCaches();
        return new ResponseEntity<>(OK);
    }
}
