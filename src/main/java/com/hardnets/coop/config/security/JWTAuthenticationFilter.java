package com.hardnets.coop.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hardnets.coop.service.impl.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Configuration
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = parseToken(request);
            if (token != null && !token.isEmpty()) {
                // Validate the token and set the authentication in the security context
                String username = jwtUtils.getUsernameFromToken(token);
                if (username != null) {
                    log.info("Username from JWT: {}", username);
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtils.validateJwtToken(token)) {
                        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        // Set the details of the authentication object
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        log.info("Setting authentication for user: {}", username);
                        log.info("Roles from JWT: {}", userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

        }catch (Exception e) {
            // If there's an error parsing the token, log it and continue
            log.error("JWT Token parsing failed: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);

    }

    private String parseToken(HttpServletRequest request) {
        String token =  jwtUtils.getJwtTokenFromHeader(request);
        log.info("JWT Token: {}", token);
        return token;
    }
}
