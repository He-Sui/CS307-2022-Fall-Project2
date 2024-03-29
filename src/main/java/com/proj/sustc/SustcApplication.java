package com.proj.sustc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.proj.sustc.mapper")
public class SustcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SustcApplication.class, args);
    }
}
