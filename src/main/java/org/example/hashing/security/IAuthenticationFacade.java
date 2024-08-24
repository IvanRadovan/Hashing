package org.example.hashing.security;

import org.example.hashing.model.AppUser;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IAuthenticationFacade {

    Authentication getAuthentication();

    Optional<AppUser> loggedInUserProvider();

}

