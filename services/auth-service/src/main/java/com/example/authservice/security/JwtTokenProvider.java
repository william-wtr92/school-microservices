package com.example.authservice.security;

import com.example.authservice.utils.ApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt-secret}")
    private String jwtSecret;

    @Value("${jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ApiException("JWT Token is expired", HttpStatus.FORBIDDEN);
        } catch (MalformedJwtException e) {
            throw new ApiException("JWT Token is invalid", HttpStatus.FORBIDDEN);
        } catch (SecurityException e) {
            throw new ApiException("JWT Token is not secure", HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            throw new ApiException("JWT Token claims string is empty", HttpStatus.FORBIDDEN);
        } catch (UnsupportedJwtException e) {
            throw new ApiException("JWT Token unsupported", HttpStatus.FORBIDDEN);
        }
    }
}
