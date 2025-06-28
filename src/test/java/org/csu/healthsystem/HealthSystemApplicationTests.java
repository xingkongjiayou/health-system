package org.csu.healthsystem;



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

//        String jwt= Jwts
//        System.out.println(jwt);
    }

}
