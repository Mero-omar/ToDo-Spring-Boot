package org.example.todo.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JWTService {

    private final static String Secret_key="eCVUNl3lVIwfUwRTwI5QeH7BFzqu1cjqZjvBLGUG3LBlndd7r5VHS3VpGBZBkz7P";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
       final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBites= Decoders.BASE64.decode(Secret_key);
        return Keys.hmacShaKeyFor(keyBites);

    }


    public String generateToken(Map<String,Object>extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }


    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username=extractUsername(token);
        return (userDetails.getUsername().equals(username)&& !isTokenExpire(token));

    }

    public boolean isTokenExpire(String token) {
        return  extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}
