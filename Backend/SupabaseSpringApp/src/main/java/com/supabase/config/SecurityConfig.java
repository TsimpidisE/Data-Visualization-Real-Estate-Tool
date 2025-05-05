package com.supabase.config;

import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.text.ParseException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/user/admin").hasRole("admin")
                .anyRequest().authenticated()
            )
            .addFilterBefore((request, response, chain) -> {
                HttpServletRequest req = (HttpServletRequest) request;
                String header = req.getHeader("Authorization");

                if (header != null && header.startsWith("Bearer ")) {
                    String token = header.substring(7);
                    try {
                        SignedJWT jwt = SignedJWT.parse(token);
                        String email = jwt.getJWTClaimsSet().getStringClaim("email");
                        String role = jwt.getJWTClaimsSet().getStringClaim("role");

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                email, null, Collections.singleton(() -> "ROLE_" + role.toLowerCase()));
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } catch (ParseException ignored) {}
                }

                chain.doFilter(request, response);
            }, BasicAuthenticationFilter.class);

        return http.build();
    }
}
