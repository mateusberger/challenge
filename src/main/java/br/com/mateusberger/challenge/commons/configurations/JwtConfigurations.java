package br.com.mateusberger.challenge.commons.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "challenge.config.jwt")
public class JwtConfigurations {

    private String secret;
    private Long expirationTimeInMinutes;

}
