package org.example.hashing.security;

import org.example.hashing.configuration.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GitHubOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private IntegrationProperties integrationProperties;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var users = integrationProperties.getOauth2SecurityProperties();
        OAuth2User oauth2User = super.loadUser(userRequest);

        Map<String, Object> userAttributes = oauth2User.getAttributes();
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("USER"));

        if (users.getAdminOne().equals(userAttributes.get("login"))) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        if (users.getAdminTwo().equals(userAttributes.get("login"))) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return new GitHubOAuth2User(oauth2User, authorities);
    }
}