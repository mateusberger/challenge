package br.com.mateusberger.challenge.order.domain;

import br.com.mateusberger.challenge.user.domain.User;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ORDER_ENTITY")
public class Order {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @ElementCollection(fetch = EAGER)
    private List<Item> items;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public BigDecimal getTotalValue() {

        if (isNull(items)) return ZERO;

        return items.stream()
                .filter(item -> nonNull(item.getUnityValue()))
                .filter(item -> nonNull(item.getQuantity()))
                .filter(item -> item.getQuantity() >= 0)
                .filter(item -> item.getUnityValue().signum() >= 0)
                .map(item -> item.getUnityValue().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(ZERO, BigDecimal::add);
    }
}
