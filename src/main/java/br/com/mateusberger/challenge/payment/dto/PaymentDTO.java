package br.com.mateusberger.challenge.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {

    private Long id;

    private Boolean isPaid;

    private Long orderId;

    private BigDecimal paidValue;
}
