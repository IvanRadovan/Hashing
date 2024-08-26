package org.example.hashing.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
@AllArgsConstructor
public class GitHubOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;
    private String oauth2ClientName;

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("login");
    }

    public String getAvatarUrl() {
        return oauth2User.getAttribute("avatar_url");
    }

}