package org.example.hashing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private GitHubOAuth2UserService gitHubOAuth2UserService;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/login", "/hash", "/decrypt").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/templates/**").permitAll()
                        .anyRequest().authenticated())
//                .formLogin(form -> form.loginPage("/login")
//                        .defaultSuccessUrl("/")
//                        .permitAll())
//                .oauth2Login(Customizer.withDefaults())
                .oauth2Login(oAuth2Login -> {
//                    oAuth2Login.loginPage("/login");
                    oAuth2Login.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(gitHubOAuth2UserService));
                })
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll())
                .build();
    }

    //    @Bean
//    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryReactiveClientRegistrationRepository(this.githubClientRegistration());
//    }
//
//    private ClientRegistration githubClientRegistration() {
//        return ClientRegistrations.fromIssuerLocation("https://github.com")  // Pre-built GitHub configuration
//                .clientId(dotenv.get("GITHUB_CLIENT_ID"))  // Fetching from .env
//                .clientSecret(dotenv.get("GITHUB_CLIENT_SECRET"))  // Fetching from .env
//                .build();
//    }

//    // https://spring.io/guides/tutorials/spring-boot-oauth2
//    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return authorities -> {
//            List<SimpleGrantedAuthority> mappedAuthorities = new ArrayList<>();
//            authorities.forEach(authority -> {
//                if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {
//                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//                    if (userAttributes.get("login").equals("IvanRadovan"))
//                        mappedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
//                }
//            });
//            return mappedAuthorities;
//        };
//    }

}
