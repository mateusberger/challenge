package br.com.mateusberger.challenge.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDTO {


    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String password;

}
