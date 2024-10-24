package br.com.mateusberger.challenge;

import br.com.mateusberger.challenge.commons.configurations.AdminUserConfigurations;
import br.com.mateusberger.challenge.commons.configurations.JwtConfigurations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties({
		JwtConfigurations.class,
		AdminUserConfigurations.class
})
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}
}
