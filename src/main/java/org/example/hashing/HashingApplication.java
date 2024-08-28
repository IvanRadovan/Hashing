package org.example.hashing;

import org.example.hashing.utility.FileSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Objects;

@SpringBootApplication
@EnableWebSecurity
public class HashingApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(HashingApplication.class);

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

    @Override
    public void run(String... args) {
        LOG.info("CommandLineRunner: Executing.");
        var fileName = FileSupplier.hashFileContent("common-passwords.txt");
        LOG.info("CommandLineRunner : New file '{}' was created.", fileName);
        FileSupplier.decryptPassword("6bd8937a8789a3e58489c4cfd514b1a7"); //hej123
    }
}
