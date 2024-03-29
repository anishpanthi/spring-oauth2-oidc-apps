package dev.app.resource.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * @author Anish Panthi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserInfo(
    String sub,
    String aud,
    String uid,
    String givenName,
    String sn,
    String providerid,
    String mail,
    String displayName,
    String app,
    String env,
    List<Group> group,
    String scp,
    Long iat,
    Long exp,
    Long nbf) {

}
