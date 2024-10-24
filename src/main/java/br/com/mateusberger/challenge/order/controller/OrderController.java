package br.com.mateusberger.challenge.order.controller;

import br.com.mateusberger.challenge.commons.security.SecurityHolderComponent;
import br.com.mateusberger.challenge.order.dto.CreateNewOrderDTO;
import br.com.mateusberger.challenge.order.dto.OrderDTO;
import br.com.mateusberger.challenge.order.service.OrderService;
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

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final SecurityHolderComponent securityHolderComponent;

    @PostMapping
    public ResponseEntity<OrderDTO> createNewOrder(@Valid @RequestBody CreateNewOrderDTO request

    ) {

        UserPublicInformation authenticatedUser = securityHolderComponent.getAuthenticatedUser();

        return ResponseEntity.ok(orderService
                .createNewOrder(
                        authenticatedUser,
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> listOrdersFromAuthenticatedUser() {

        UserPublicInformation authenticatedUser = securityHolderComponent.getAuthenticatedUser();

        return ResponseEntity.ok(orderService
                .listOrdersFromAuthenticatedUser(
                        authenticatedUser
                )
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@Positive @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
