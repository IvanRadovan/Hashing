package se.systementor.javasecstart.security;

import org.springframework.security.core.Authentication;
import se.systementor.javasecstart.model.AppUser;

import java.util.Optional;

public interface IAuthenticationFacade {

    Authentication getAuthentication();

    Optional<AppUser> loggedInUserProvider();

}

