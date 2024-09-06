package org.example.hashing.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2SecurityProperties {

    private String gitHubClientId;
    private String gitHubClientSecret;
    private String adminOne;
    private String adminTwo;

}
