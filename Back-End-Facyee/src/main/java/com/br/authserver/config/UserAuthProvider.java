package com.br.authserver.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.br.authserver.dtos.UserDto;


import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Base64;
import java.util.Collections;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

	
	private String secretKey = "secret";
	
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken (UserDto dto) {
		Instant date = Instant.now().plusSeconds(3600000L);
		return JWT.create()
				.withIssuer(dto.getUsername())
				.withIssuedAt(Instant.now())
				.withExpiresAt(date)
				.withClaim("realName", dto.getRealName()).sign(Algorithm.HMAC256(secretKey));
				
	}
	public Authentication validateToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decoded= verifier.verify(token);
		UserDto user = UserDto.builder()
				.username(decoded.getIssuer())
				.realName(decoded.getClaim("realName").asString()).build();
		
		return new UsernamePasswordAuthenticationToken(user, null,Collections.emptyList());
	}
	
}
