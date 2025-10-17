package com.firstSpring.First.Spring.Configuration;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final String SECRET = "I am Naveenbabu. My first spring project. I an using this sentence as key";
    private final long expirationTime = 1000*60*60;//1 hour
    private final SecretKey secretkey = Keys.hmacShaKeyFor(SECRET.getBytes());


    public String generateToken(String email){

       return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretkey, Jwts.SIG.HS384) // ✅ modern way
            .compact();
    }

    public String extractEmail(String token) {

       return Jwts.parser()
            .verifyWith(secretkey) // ✅ replaces deprecated setSigningKey()
            .build()
            .parseSignedClaims(token) // ✅ replaces parseClaimsJws()
            .getPayload()
            .getSubject(); // ✅ safely gets the email
    }
    public String extractEmailFromHeader(String authHeader){
        String token = authHeader.substring(7); // remove "Bearer "
        return extractEmail(token); // jwtUtils is your JwtUtils class
    }

    public Boolean validateToken(String token){
        try {
            extractEmail(token);
            return true;
        } catch (JwtException e) {
            return false;
        }

    }
}
