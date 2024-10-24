package br.com.mateusberger.challenge.commons.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "challenge.config.admin-user")
public class AdminUserConfigurations {
    private Boolean create;
    private String username;
    private String password;
}
