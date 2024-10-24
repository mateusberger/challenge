package br.com.mateusberger.challenge.auth.service;

import br.com.mateusberger.challenge.auth.dto.AuthTokenDTO;
import br.com.mateusberger.challenge.auth.dto.AuthenticateDTO;
import br.com.mateusberger.challenge.commons.security.JwtService;
import br.com.mateusberger.challenge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public AuthTokenDTO authenticate(AuthenticateDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthTokenDTO("Bearer " + jwtToken);
    }
}
