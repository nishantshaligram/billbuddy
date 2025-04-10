package in.techarray.billbuddy.user_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpiration = 1000 * 60 * 60;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes( StandardCharsets.UTF_8 ));
    }
    
    /**
     * Generates a JWT (JSON Web Token) for the given username.
     *
     * @param username the username for which the token is to be generated
     * @return the generated JWT as a String
     */
    public String generateToken(String username) {
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration( new Date( System.currentTimeMillis() + jwtExpiration) )
            .signWith( secretKey, Jwts.SIG.HS256 )
            .compact();
    }

    public boolean validateToken( String token ) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException  e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claims resolver function.
     *
     * @param <T> the type of the claim to be extracted
     * @param token the JWT token from which the claim is to be extracted
     * @param claimsResolver a function that takes Claims and returns the desired claim
     * @return the extracted claim of type T
     */
    private <T> T extractClaim( String token, Function<Claims, T> claimsResolver ) {
        final Claims claims = Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token).getPayload();
        return claimsResolver.apply(claims);
    }

    /**
     * Checks if the provided JWT token is expired.
     *
     * @param token the JWT token to be checked
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired( String token ) {
        return extractClaim(token, Claims::getExpiration ).before( new Date() );
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token from which the username is to be extracted
     * @return the extracted username as a String
     */
    public String extractUsername( String token ) {
        return extractClaim(token, Claims::getSubject );
    }

    /**
     * Validates the provided JWT token against the given username.
     *
     * @param token the JWT token to be validated
     * @param username the username to be checked against the token
     * @return true if the token is valid and matches the username, false otherwise
     */
    public boolean validateToken( String token, String username  ) {
        String extractedUsername = extractUsername(token);
        return ( extractedUsername.equals( username ) && ! isTokenExpired( token ) );
    }
}
