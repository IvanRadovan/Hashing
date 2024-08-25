package org.example.hashing.service;

import org.example.hashing.model.AppUser;
import org.example.hashing.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AppUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser findUserById(UUID id) {
        return appUserRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public boolean addUser(AppUser user) {
        return appUserRepository.findByUsername(user.getUsername())
                .map(foundUser -> {
                    LOGGER.warn("Username '{}' is already taken.", foundUser.getUsername());
                    return false;
                })
                .orElseGet(() -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setEnabled(!user.getRoles().isEmpty());
                    appUserRepository.save(user);
                    LOGGER.info("User '{}' was added.", user.getUsername());
                    return true;
                });
    }
}
