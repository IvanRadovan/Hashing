package org.example.hashing.configuration;

import org.example.hashing.model.AppUser;
import org.example.hashing.model.Role;
import org.example.hashing.repository.AppUserRepository;
import org.example.hashing.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.hashing.utility.FileSupplier.getFile;

// TODO: Try to improve this class
@Service
public class AppUserDataSeeder {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    IntegrationProperties integrationProperties;

    public void seedAppUser() {
        String role = integrationProperties.getUserData().getRole();
        String username = integrationProperties.getUserData().getUsername();

        if (roleRepository.findByName(role).isEmpty())
            addRole(role);

        if (appUserRepository.findByUsername(username).isEmpty())
            addAppUser(username, role);
    }

    private void addAppUser(String username, String roleName) {
        String password = integrationProperties.getUserData().getPassword();
        String alias = integrationProperties.getUserData().getAlias();
        String dirPath = integrationProperties.getUserData().getProfileDir();
        String relativeDirPath = integrationProperties.getUserData().getRelativeProfileDir();
        String fileName = integrationProperties.getUserData().getDefaultProfile();
        String profilePicture = getFile(dirPath, fileName).orElseThrow(NoSuchFieldError::new);

        List<Role> roles = new ArrayList<>();
        Role role = roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new NoSuchElementException("Role does not exist"));
        roles.add(role);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        AppUser appUser = AppUser.builder()
                .username(username)
                .password(hash)
                .roles(roles)
                .alias(alias)
                .profilePicture(relativeDirPath.concat(profilePicture))
                .enabled(true)
                .build();
        appUserRepository.save(appUser);
    }

    private void addRole(String name) {
        roleRepository.save(Role.builder().name(name).build());
    }
}
