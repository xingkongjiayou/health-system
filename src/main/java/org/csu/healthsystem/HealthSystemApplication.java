package org.csu.healthsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.Mapping;

@SpringBootApplication
@MapperScan("org.csu.healthsystem.util")
public class HealthSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthSystemApplication.class, args);
    }

}
