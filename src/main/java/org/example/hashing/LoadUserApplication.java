package org.example.hashing;

import org.example.hashing.configuration.AppUserDataSeeder;
import org.example.hashing.configuration.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties(IntegrationProperties.class)
public class LoadUserApplication implements CommandLineRunner {

    @Autowired
    private AppUserDataSeeder appUserDataSeeder;

    @Override
    public void run(String... args) {
        appUserDataSeeder.seedAppUser();
    }
}
