package com.agustin.backend.usersapp.backend_usersapp.config.utils;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken (Authentication authentication){
        
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority) // /grantedAuthoritie -> grantedAuthoritie.getAuthority())
        .collect(Collectors.joining(","));

        String jwtToken = JWT.create()
        .withIssuer(this.userGenerator)
        .withSubject(username)
        .withClaim("authorities", authorities)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis()+ 1800000))
        .withJWTId(UUID.randomUUID().toString())
        .withNotBefore(new Date(System.currentTimeMillis())) //Definir desde que momento es valido el token
        .sign(algorithm); //Se establece la firma

        return jwtToken;
    }

    //Metodo de verificacion para el token
    public DecodedJWT validateToken(String token) {
        
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(this.userGenerator)
            .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;

        } catch (Exception e) {
            throw new JWTVerificationException("Token invalido, no esta autorizado");
        }
    }

    public String extractUsername (DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }

}
