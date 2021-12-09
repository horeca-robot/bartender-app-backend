package com.horecarobot.backend.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.horecarobot.backend.Exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    private final Algorithm jwtHashAlgorithm;

    public AuthService(Algorithm jwtHashAlgorithm) {
        this.jwtHashAlgorithm = jwtHashAlgorithm;
    }

    public String createJWT(Map<String, String> claims) {
        Date expirationDate = new Date();
        expirationDate.setTime(expirationDate.getTime() + this.getTokenExpirationTime()); // Expires in 1 hour

        JWTCreator.Builder tokenBuilder = JWT.create()
                .withIssuer("bartender-backend")
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate);

        claims.forEach(tokenBuilder::withClaim);

        return tokenBuilder.sign(this.jwtHashAlgorithm);
    }

    public boolean tokenIsValid(String token, UUID givenUUID) {
        JWTVerifier tokenVerification = JWT.require(this.jwtHashAlgorithm)
                .withClaim("uid", givenUUID.toString())
                .build();

        DecodedJWT decodedToken = JWT.decode(token);

        try {
            tokenVerification.verify(decodedToken);
        } catch(JWTVerificationException exception) {
            return false;
        }

        return true;
    }

    public String refreshToken(String token, UUID givenUUID) throws InvalidTokenException {
        if(!this.tokenIsValid(token, givenUUID)) {
            throw new InvalidTokenException("Token is invalid");
        }

        DecodedJWT jwt = JWT.decode(token);
        Map<String, String> claims = new HashMap<String, String>();

        claims.put("uid", jwt.getClaim("uid").asString());

        return this.createJWT(claims);
    }

    private int getTokenExpirationTime() {
        return (1000 * 60 * 60 * 1);
    }
}
