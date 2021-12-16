package com.horecarobot.backend.Configuration;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class JWTConfiguration {
    private final Environment env;

    @Autowired
    public JWTConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public Algorithm algorithm() {
        String jwtSecret = Objects.requireNonNull(env.getProperty("horecarobot.jwt.secret"));

        return Algorithm.HMAC256(jwtSecret);
    }
}
