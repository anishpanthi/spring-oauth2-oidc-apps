package dev.app.resource.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class OAuth2MultiTenancyApplication {

  public static void main(String[] args) {
    SpringApplication.run(OAuth2MultiTenancyApplication.class, args);
  }

  @Bean
  public RestClient restClient() {
    return RestClient.builder().build();
  }

}
