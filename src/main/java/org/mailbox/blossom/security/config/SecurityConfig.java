package org.mailbox.blossom.security.config;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.constant.Constants;
import org.mailbox.blossom.security.filter.GlobalLoggerFilter;
import org.mailbox.blossom.security.filter.JwtAuthenticationFilter;
import org.mailbox.blossom.security.filter.JwtExceptionFilter;
import org.mailbox.blossom.security.handler.jwt.JwtAccessDeniedHandler;
import org.mailbox.blossom.security.handler.jwt.JwtAuthEntryPoint;
import org.mailbox.blossom.security.handler.oauth2.OAuth2LoginFailureHandler;
import org.mailbox.blossom.security.handler.oauth2.OAuth2LoginSuccessHandler;
import org.mailbox.blossom.security.handler.signout.CustomSignOutProcessHandler;
import org.mailbox.blossom.security.handler.signout.CustomSignOutResultHandler;
import org.mailbox.blossom.security.service.CustomOAuth2UserService;
import org.mailbox.blossom.security.service.CustomUserDetailService;
import org.mailbox.blossom.utility.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    private final CustomUserDetailService customUserDetailService;

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                                .requestMatchers(Constants.ADMIN_URLS.toArray(String[]::new)).hasRole("ADMIN")
                                .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()
                )

                .formLogin(AbstractHttpConfigurer::disable)

                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
                                .successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                )

                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/sign-out")
                        .addLogoutHandler(customSignOutProcessHandler)
                        .logoutSuccessHandler(customSignOutResultHandler)
                )

                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, customUserDetailService),
                        LogoutFilter.class)
                .addFilterBefore(
                        new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class)
                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        JwtExceptionFilter.class)

                .getOrBuild();
    }
}
