package devapps.oauth2.server;

import java.util.Set;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @author Anish Panthi
 */
@Configuration
public class ApiConfig {

  @Bean
  RegisteredClientRepository registeredClientRepository(JdbcTemplate template) {
    return new JdbcRegisteredClientRepository(template);
  }

  @Bean
  ApplicationRunner usersRunner(UserDetailsManager userDetailsManager) {
    return args -> {
      var firstUser =
          User.builder()
              .username("anish")
              .password("{bcrypt}$2a$10$lnms/p2Gv37GcmDnu3N/luAXPTmreC7kEOJc5nkT9.Ae/i2.fS3sW")
              .roles("ADMIN")
              .build();
      if (!userDetailsManager.userExists(firstUser.getUsername())) {
        userDetailsManager.createUser(firstUser);
      }
    };
  }

  @Bean
  JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }

  @Bean
  ApplicationRunner clientsRunner(RegisteredClientRepository repository) {
    return args -> {
      var clientId = "pamiadu";
      if (repository.findByClientId(clientId) == null) {
        repository.save(
            RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(
                    "{bcrypt}$2a$10$RhLDi5hKxNqBQcSkfRdIUemy9NGGKZZ/0J7y4BDNJbPtKYcZEgTnC")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantTypes(
                    grantTypes ->
                        grantTypes.addAll(
                            Set.of(
                                AuthorizationGrantType.CLIENT_CREDENTIALS,
                                AuthorizationGrantType.AUTHORIZATION_CODE,
                                AuthorizationGrantType.REFRESH_TOKEN)))
                .redirectUri("http://127.0.0.1:8081/login/oauth2/code/spring")
                .scopes(
                    scopes -> scopes.addAll(Set.of("user.read", "user.write", OidcScopes.OPENID)))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build());
      }
    };
  }

  @Bean
  JdbcOAuth2AuthorizationConsentService jdbcOAuth2AuthorizationConsentService(
      JdbcOperations jdbcOperations, RegisteredClientRepository repository) {
    return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, repository);
  }

  @Bean
  JdbcOAuth2AuthorizationService jdbcOAuth2AuthorizationService(
      JdbcOperations jdbcOperations, RegisteredClientRepository rcr) {
    return new JdbcOAuth2AuthorizationService(jdbcOperations, rcr);
  }
}
