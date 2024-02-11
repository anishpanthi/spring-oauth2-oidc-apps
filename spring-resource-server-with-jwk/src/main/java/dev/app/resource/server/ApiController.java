package dev.app.resource.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Anish Panthi
 */
@RestController
@RequestMapping("/api")
public class ApiController {

  @GetMapping("/greet")
  public Map<String, String> greet() {
    return Map.of(
        "message", "Hello World! Welcome to Spring's OAuth 2.0 Resource Server implementation");
  }
}
