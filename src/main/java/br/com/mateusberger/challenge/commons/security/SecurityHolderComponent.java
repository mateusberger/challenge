package br.com.mateusberger.challenge.commons.security;

import br.com.mateusberger.challenge.commons.exceptions.FailToValidateAuthenticationException;
import br.com.mateusberger.challenge.commons.exceptions.UnauthorizedUserException;
import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.UserPublicInformation;
import br.com.mateusberger.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SecurityHolderComponent {

    private final UserRepository userRepository;

    public UserPublicInformation getAuthenticatedUser(){
        var username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(username)
                .orElseThrow(FailToValidateAuthenticationException::new);
    }

    public UserPublicInformation getAuthenticatedUserIfHasRoles(RoleEnum... roles){
        UserPublicInformation authenticatedUser = getAuthenticatedUser();
        boolean matchRoles = Arrays.stream(roles)
                .anyMatch(roleEnum -> authenticatedUser.getRole().equals(roleEnum));

        if (matchRoles) return authenticatedUser;

        throw new UnauthorizedUserException();
    }
}
