package br.com.mateusberger.challenge.payment.domain;

import br.com.mateusberger.challenge.order.domain.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PAYMENT_ENTITY")
public class Payment {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    private Boolean isPaid;

    private BigDecimal valuePaid;
}