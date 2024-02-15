package devapps.oauth2.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Anish Panthi
 */
@Configuration
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails anish = User.builder()
        .username("anish")
        .password("{noop}panthi")
        .roles("ADMIN", "USER")
        .build();
    return new InMemoryUserDetailsManager(anish);
  }

}
