package org.csu.healthsystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    public static String generateToken(Map<String,Object> map) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"mirtable233")
                .addClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000))
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey("mirtable233")
                .parseClaimsJws(token).getBody();
    }
    }
