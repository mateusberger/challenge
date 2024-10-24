package br.com.mateusberger.challenge.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {

    @NotNull
    @NotBlank
    private String itemName;

    @NotNull
    @Positive
    private Long quantity;

    @NotNull
    @Positive
    private BigDecimal unityValue;
}