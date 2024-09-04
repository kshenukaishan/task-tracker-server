package com.ishan.task_tracker_server.config;

import com.ishan.task_tracker_server.enums.UserRole;
import com.ishan.task_tracker_server.services.jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CorsConfiguration
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of(
                "Authorization","Cache-Control","Content-Type"
        ));
        // Allow methods for cors configurations
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        //  Requesting for auth header
        configuration.setAllowCredentials(false);
        configuration.setExposedHeaders(List.of("Authorization"));

        http
                .csrf(AbstractHttpConfigurer::disable) // cross site forgery
                .cors(cors -> cors.configurationSource(request -> configuration)) // (4200) allow different origin/port
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**").permitAll() // permit all for guest users
                        .requestMatchers("/api/v1/admin/**").hasAuthority(UserRole.ADMIN.name()) // request matchers for endpoints and Allocating Roles
                        .requestMatchers("/api/v1/employee/**").hasAnyAuthority(UserRole.EMPLOYEE.name()) // request matchers for endpoints and Allocating Roles
                        .anyRequest() // any request with
                        .authenticated() // authentication will be allowed
                ).sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session management to STATELESS
                .authenticationProvider(authenticationProvider()) // Set authentication provider bean
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //
        return http.build(); // Build http configured

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService.userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
        // Set User details service for DAoAuthentication provider and return it.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // Create PasswordEncoder for return BCryptPasswordEncoder
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
        // Return authentication manager from auth configuration
    }


}
