package br.com.mateusberger.challenge.auth.controller;

import br.com.mateusberger.challenge.auth.dto.AuthTokenDTO;
import br.com.mateusberger.challenge.auth.dto.AuthenticateDTO;
import br.com.mateusberger.challenge.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthTokenDTO> authenticate(
            @Valid @RequestBody AuthenticateDTO request
    ) {
        return ok(authService.authenticate(request));
    }
}
