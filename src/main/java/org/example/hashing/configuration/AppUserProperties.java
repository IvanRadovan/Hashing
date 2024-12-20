package org.example.hashing.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserProperties  {

    private String username;
    private String password;
    private String role;
    private String alias;
    private String defaultProfile;
    private String blankProfile;

}
