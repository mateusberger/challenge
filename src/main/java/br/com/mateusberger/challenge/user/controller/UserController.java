package br.com.mateusberger.challenge.user.controller;

import br.com.mateusberger.challenge.commons.security.SecurityHolderComponent;
import br.com.mateusberger.challenge.user.domain.UserPublicInformation;
import br.com.mateusberger.challenge.user.dto.CreateUserDTO;
import br.com.mateusberger.challenge.user.dto.EditUserDTO;
import br.com.mateusberger.challenge.user.dto.UserDTO;
import br.com.mateusberger.challenge.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.mateusberger.challenge.user.domain.RoleEnum.ADMIN;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final SecurityHolderComponent securityHolderComponent;

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> editUser(
            @Positive @PathVariable Long userId,
            @Valid @RequestBody EditUserDTO request
    ) {
        securityHolderComponent.getAuthenticatedUserIfHasRoles(ADMIN);

        return ok(userService.editUserInformation(userId, request));
    }

    @GetMapping
    public ResponseEntity<UserPublicInformation> getCurrentUser(
    ) {
        UserPublicInformation authenticatedUser = securityHolderComponent.getAuthenticatedUser();
        return ok(userService.getUserInformation(authenticatedUser));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createNewUser(
            @Valid @RequestBody CreateUserDTO request
    ) {
        securityHolderComponent.getAuthenticatedUserIfHasRoles(ADMIN);

        return ok(userService.createNewUser(request));
    }
}
