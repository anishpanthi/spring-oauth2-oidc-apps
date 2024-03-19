package dev.app.resource.server;

import java.security.Principal;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anish Panthi
 */
@RestController
@Log4j2
@ResponseBody
public class ApiController {

  private final OAuth2AuthorizedClientService authorizedClientService;

  public ApiController(OAuth2AuthorizedClientService authorizedClientService) {
    this.authorizedClientService = authorizedClientService;
  }

  @GetMapping("/api/greet")
  public Map<String, String> greet(Principal principal) {

    OAuth2AccessToken accessToken =
        authorizedClientService
            .loadAuthorizedClient("okta", principal.getName())
            .getAccessToken();
    OAuth2RefreshToken refreshToken =
        authorizedClientService
            .loadAuthorizedClient("okta", principal.getName())
            .getRefreshToken();
    log.info("Access Token: {}", accessToken.getTokenValue());
    log.info(
        "Refresh Token: {}",
        refreshToken == null ? "null" : refreshToken.getTokenValue());
    return Map.of(
        "message",
        "Hello " + principal.getName() + "! Welcome to Spring's OAuth2.0 & OpenID Connect demo");
  }

  @GetMapping
  public Map<String, Object> secure(@AuthenticationPrincipal OidcUser oidcUser) {
    log.info("User details: {}", oidcUser);
    return Map.of(
        "message",
        "Hello World! Welcome to Spring's OAuth2.0 & OpenID Connect demo",
        "claims",
        oidcUser.getClaims());
  }
}
