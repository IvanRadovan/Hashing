package org.example.hashing.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GitHubOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;
    private Collection<GrantedAuthority> additionalAuthorities;

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("login");
    }

    public String getAvatarUrl() {
        return oauth2User.getAttribute("avatar_url");
    }

    public String getProfileUrl() {
        return oauth2User.getAttribute("html_url");
    }

}