package com.hardnets.coop.controller;

import com.hardnets.coop.dto.request.UserSignupRequest;
import com.hardnets.coop.dto.response.LoginDto;
import com.hardnets.coop.entity.UserEntity;
import com.hardnets.coop.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/v1/public/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/signup")
    public ResponseEntity<LoginDto> signup(@RequestBody @Valid UserSignupRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            UserEntity user = (UserEntity) authenticate.getPrincipal();
            LoginDto response = new LoginDto();
            response.setRut(user.getRut());
            response.setEmail(user.getEmail());
            response.setToken(jwtTokenUtil.generateAccessToken(user));
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, response.getToken())
                    .body(response);
        } catch (BadCredentialsException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
