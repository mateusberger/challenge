package br.com.mateusberger.challenge.payment.controller;

import br.com.mateusberger.challenge.commons.security.SecurityHolderComponent;
import br.com.mateusberger.challenge.payment.dto.PaymentDTO;
import br.com.mateusberger.challenge.payment.dto.ProcessPaymentDTO;
import br.com.mateusberger.challenge.payment.service.PaymentService;
import br.com.mateusberger.challenge.user.domain.UserPublicInformation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.mateusberger.challenge.user.domain.RoleEnum.ACCOUNT_MANAGER;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final SecurityHolderComponent securityHolderComponent;

    @PostMapping
    public ResponseEntity<PaymentDTO> processPayment(@Valid @RequestBody ProcessPaymentDTO request) {

        var user = securityHolderComponent.getAuthenticatedUserIfHasRoles(ACCOUNT_MANAGER);

        return ResponseEntity.ok(paymentService.processPayment(
                user,
                request.getOrderId())
        );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@Positive @PathVariable Long paymentId) {

        UserPublicInformation user = securityHolderComponent.getAuthenticatedUser();

        return ResponseEntity.ok(paymentService.getPayment(
                user,
                paymentId)
        );
    }
}
