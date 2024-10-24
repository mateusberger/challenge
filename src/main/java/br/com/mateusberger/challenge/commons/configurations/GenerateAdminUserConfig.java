package br.com.mateusberger.challenge.commons.configurations;

import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import br.com.mateusberger.challenge.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class GenerateAdminUserConfig {

    private final AdminUserConfigurations adminUserConfigurations;

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminUser() {


        if (!adminUserConfigurations.getCreate()) return;

        Optional<User> user = userRepository.findByUsername(adminUserConfigurations.getUsername());

        if (user.isEmpty()) {

            userRepository.save(new User(
                            null,
                            adminUserConfigurations.getUsername(),
                            adminUserConfigurations.getPassword(),
                            RoleEnum.ADMIN
                    )
            );
        }

    }
}
