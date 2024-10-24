package br.com.mateusberger.challenge.user.dto;

import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.UserPublicInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements UserPublicInformation {

    private Long id;

    private String username;

    private RoleEnum role;
}
