package br.com.mateusberger.challenge.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateNewOrderDTO {

    @NotNull
    private List<ItemDTO> items;
}
