package ru.myrating.application.web.rest;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myrating.application.security.AuthoritiesConstants;
import ru.myrating.application.service.PaymentService;
import ru.myrating.application.service.dto.payment.PaymentUserDTO;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/payment")
public class PaymentResource {
    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<PaymentUserDTO>> getAllPayment(@RequestParam(required = false) Integer year, Sort sort) throws IOException {
        return new ResponseEntity<>(paymentService.getAllPayments(year, sort), OK);
    }
}
