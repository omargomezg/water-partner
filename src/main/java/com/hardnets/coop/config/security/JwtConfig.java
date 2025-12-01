package com.hardnets.coop.config.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class JwtConfig {

	private static final String JWT_KEY = "assdadasdhaoidhjiou398745283jdstxyoasidhfoi;h30p972566";

	@Bean
	JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(JWT_KEY.getBytes()));
	}

	@Bean
	JwtDecoder jwtDecoder() {
		byte[] bytes = JWT_KEY.getBytes();
		SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length, "RSA");
		return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

}
