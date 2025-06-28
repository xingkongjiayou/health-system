package org.csu.healthsystem;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class HealthSystemApplicationTests {

    @Test
    void testjwt() {
        Map<String,Object> map = new HashMap<>();
        map.put("username","admin");
        map.put("password","123456");

        String jwt= Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256,"mirtable2333")
                                .addClaims(map)
                                        .setExpiration(new Date(System.currentTimeMillis()+3600*1000))
                                                .compact();
        System.out.println(jwt);
    }
    @Test
    void testParseJwt(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJuYW1lIjoiYWRtaW4iLCJleHAiOjE3NTExMjQyMzh9.oz59GOI-Cphdq1xtKEzDIutnRPKO9PTnrUb5ONbrVsc";
        Claims claims=Jwts.parser()
                .setSigningKey("mirtable2333")
                .parseClaimsJws(token).getBody();
        System.out.println(claims);
    }

}
