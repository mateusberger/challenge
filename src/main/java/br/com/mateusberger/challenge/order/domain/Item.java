package br.com.mateusberger.challenge.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item{

    private String itemName;
    private Long quantity;
    private BigDecimal unityValue;

}