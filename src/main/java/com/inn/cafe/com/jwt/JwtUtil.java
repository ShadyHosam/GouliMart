package com.inn.cafe.com.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.sql.RowSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtUtil {

    private String secretKey = "anythingsecret";
    private UserDetails userDetails;

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }
    public <T> T extractClaims(String token , Function<Claims,T> claimResolver){
        final Claims claims= extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username , String role){
        Map<String ,Object> claims = new HashMap<>();
        claims.put("role",role);
        return createToken(claims,username);

    }
    private String createToken(Map<String ,Object> claims , String subject){
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,secretKey).compact();
    }
    public boolean validateToken(String token){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
