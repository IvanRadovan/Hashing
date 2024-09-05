package org.example.hashing.security;

import org.example.hashing.model.AppUser;
import org.example.hashing.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Autowired
    private AppUserService appUserService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Optional<AppUser> loggedInUserProvider() {
        Authentication authentication = getAuthentication();
        return (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName()))
            ? Optional.ofNullable(appUserService.findUserByUsername(authentication.getName()))
            : Optional.empty();
    }
}
