package org.example.hashing.security;

import org.example.hashing.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private AppUserRepository appUserRepository;

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
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration githubRegistration = CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("Ov23liL7atM2ux4KpqUY")
                .clientSecret("be86f1741f7ea9c373a15fd88e55f1c9fe7883f6")
                .build();
        return new InMemoryClientRegistrationRepository(githubRegistration);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/logout/**", "/login/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/templates/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .formLogin(form -> form
                        .loginPage("/login")
//                        .defaultSuccessUrl("/login/success", true)
//                        .failureUrl("/login?error=true")
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll())
                .oauth2Login(login -> login
                        .loginPage("/login")
//                        .authorizationEndpoint(endPoint -> endPoint.baseUri("/login/oauth2/authorization"))
//                        .userInfoEndpoint(endPoint -> endPoint.userAuthoritiesMapper(this.userAuthoritiesMapper()))
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll())


//                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
//                        .defaultSuccessUrl("/login/success", true)
//                        .userInfoEndpoint(ep -> ep.userAuthoritiesMapper(userAuthoritiesMapper())))
                        .logout(logout -> {
                            logout.permitAll();
                            logout.logoutSuccessUrl("/logout/success");
                        })
                        .build();
    }


//    // https://spring.io/guides/tutorials/spring-boot-oauth2
//    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return authorities -> {
//            List<SimpleGrantedAuthority> mappedAuthorities = new ArrayList<>();
//            authorities.forEach(authority -> {
//                if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {
//                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//
//                    String login = userAttributes.get("login").toString();
//
//                    mappedAuthorities.add(new SimpleGrantedAuthority("MEMBER"));
//                }
//            });
//            return mappedAuthorities;
//        };
//    }


}
