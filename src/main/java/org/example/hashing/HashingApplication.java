package org.example.hashing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Objects;

@SpringBootApplication
@EnableWebSecurity
public class HashingApplication {

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(HashingApplication.class, args);
        } else {
            SpringApplication application;
            if (Objects.equals(args[0], "LoadUserApplication")) {
                application = new SpringApplication(LoadUserApplication.class);
            } else {
                throw new IllegalArgumentException("Unknown argument: %s".formatted(args[0]));
            }
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }
    }
}
