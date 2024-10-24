package br.com.mateusberger.challenge.commons.configurations;

import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import br.com.mateusberger.challenge.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static java.lang.Boolean.FALSE;

@Configuration
@RequiredArgsConstructor
public class GenerateAdminUserConfig {

    private final AdminUserConfigurations adminUserConfigurations;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminUser() {

        if (FALSE.equals(adminUserConfigurations.getCreate())) return;

        Optional<User> user = userRepository.findByUsername(adminUserConfigurations.getUsername());

        if (user.isEmpty()) {

            var encodedPassword = passwordEncoder.encode(adminUserConfigurations.getPassword());

            userRepository.save(new User(
                            null,
                            adminUserConfigurations.getUsername(),
                            encodedPassword,
                            RoleEnum.ADMIN
                    )
            );
        }

    }
}