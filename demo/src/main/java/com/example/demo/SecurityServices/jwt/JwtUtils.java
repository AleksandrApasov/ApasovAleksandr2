package com.example.demo.SecurityServices.jwt;

import com.example.demo.SecurityServices.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    //private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final Logger logger = LogManager.getLogger(JwtUtils.class);
    @Value("600000")
    private int jwtExpirationMs;

    private final String jwtSecret = "Bearereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
    SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(secret).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateJwtToken(Authentication authentication) {


        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().subject((userPrincipal.getUsername())).issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(secret).compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload().getSubject();

    }

}