package com.ecommerce.ECommerceApp.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

//import java.util.Date;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    //private String jwtSecret;
    @Value("${auth.token.expirationInMills}")
    private String expirationTime;


    public static String generateJwtSecret() {
        // Create a SecureRandom instance
        SecureRandom secureRandom = new SecureRandom();

        // Generate a random byte array (256 bits = 32 bytes)
        byte[] secretBytes = new byte[32]; // 256-bit secret
        secureRandom.nextBytes(secretBytes);

        // Encode the byte array into a Base64 string
        return Base64.getEncoder().encodeToString(secretBytes);
    }

    public String generateTokenForUser(Authentication authentication){
        ShoppingUserDetails userDetails=(ShoppingUserDetails) authentication.getPrincipal();
        List<String> roles=userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .setSubject(userDetails.getEmail())
                .claim("id",userDetails.getId())
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256).compact();
                }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(generateJwtSecret()));
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |SignatureException |IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }
}
