package com.horecarobot.backend;

import com.auth0.jwt.algorithms.Algorithm;
import com.horecarobot.backend.Auth.AuthService;
import com.horecarobot.backend.Exceptions.InvalidTokenException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTests {
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        this.authService = new AuthService(Algorithm.HMAC256("super-secret-test-key"));
    }

    @Test
    @Order(1)
    public void testIfAuthServiceIsNotNull() {
        assertNotNull(this.authService);
    }

    @Test
    @Order(2)
    public void testIfCanGetJWTString() {
        // Arrange
        Map<String, String> claims = new HashMap<String, String>();
        claims.put("uid", "c9ee25eb-2f26-4d30-a381-3547663bf197");

        // Act
        String token = this.authService.createJWT(claims);

        // Assert
        assertNotEquals(0, token.length());
    }

    @Test
    @Order(3)
    public void testIfTokenIsValid() {
        // Arrange
        String uuid = "c9ee25eb-2f26-4d30-a381-3547663bf197";

        Map<String, String> claims = new HashMap<String, String>();
        claims.put("uid", "c9ee25eb-2f26-4d30-a381-3547663bf197");

        String token = this.authService.createJWT(claims);

        // Act
        boolean validStatus = this.authService.tokenIsValid(token, UUID.fromString(uuid));

        // Assert
        assertEquals(true, validStatus);
    }

    @Test
    @Order(4)
    public void testIfTokenIsInvalidWhenNoUidClaimIsGiven() {
        // Arrange
        String uuid = "c9ee25eb-2f26-4d30-a381-3547663bf197";

        Map<String, String> claims = new HashMap<String, String>();
        String token = this.authService.createJWT(claims);

        // Act
        boolean validStatus = this.authService.tokenIsValid(token, UUID.fromString(uuid));

        // Assert
        assertEquals(false, validStatus);
    }

    @Test
    @Order(5)
    public void checkIfCanRefreshToken() throws InvalidTokenException {
        // Arrange
        String uuid = "c9ee25eb-2f26-4d30-a381-3547663bf197";

        Map<String, String> claims = new HashMap<String, String>();
        claims.put("uid", "c9ee25eb-2f26-4d30-a381-3547663bf197");

        String token = this.authService.createJWT(claims);

        // Act
        String refreshToken = this.authService.refreshToken(token, UUID.fromString(uuid));

        // Assert
        assertNotEquals(0, refreshToken.length());
    }

    @Test
    @Order(6)
    public void checkIfCannotRefreshTokenWithoutUidClaim() throws InvalidTokenException {
        // Arrange
        String uuid = "c9ee25eb-2f26-4d30-a381-3547663bf197";

        Map<String, String> claims = new HashMap<String, String>();
        String token = this.authService.createJWT(claims);

        // Act
        Exception exception = assertThrows(InvalidTokenException.class, () -> {
            this.authService.refreshToken(token, UUID.fromString(uuid));
        });

        // Assert
        assertNotNull(exception);
    }
}
