package org.example.hashing;

import org.example.hashing.configuration.AppUserDataSeeder;
import org.example.hashing.configuration.IntegrationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties(IntegrationProperties.class)
public class LoadUserApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(LoadUserApplication.class);

    @Autowired
    private AppUserDataSeeder appUserDataSeeder;

    @Override
    public void run(String... args) {
        appUserDataSeeder.seedAppUser();
        LOG.info("User data seeded");
    }
}
