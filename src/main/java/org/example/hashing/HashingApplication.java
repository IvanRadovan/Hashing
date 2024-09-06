package org.example.hashing;

import org.example.hashing.utility.HashGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class HashingApplication implements CommandLineRunner {

    @Value("${local_file.passwords}")
    private String passwords;

    @Value("${local_file.hashed_passwords}")
    private String hashedPasswords;

    private static final Logger LOG = LoggerFactory.getLogger(HashingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HashingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info("CommandLineRunner: Executing I/O");
        HashGenerator.hashFileContent(passwords, hashedPasswords);
    }
}
