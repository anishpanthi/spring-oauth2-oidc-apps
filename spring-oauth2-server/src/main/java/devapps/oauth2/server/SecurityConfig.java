package devapps.oauth2.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Anish Panthi
 */
@Configuration
public class SecurityConfig {

  @Bean
  InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    var anish =
        User.builder()
            .username("anish")
            .password("{bcrypt}$2a$10$lnms/p2Gv37GcmDnu3N/luAXPTmreC7kEOJc5nkT9.Ae/i2.fS3sW")
            .roles("ADMIN")
            .build();
    return new InMemoryUserDetailsManager(anish);
  }
}
