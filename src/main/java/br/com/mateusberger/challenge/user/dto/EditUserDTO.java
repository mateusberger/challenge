package br.com.mateusberger.challenge.user.dto;


import br.com.mateusberger.challenge.user.domain.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditUserDTO {

    private RoleEnum role;

    private String username;
}
