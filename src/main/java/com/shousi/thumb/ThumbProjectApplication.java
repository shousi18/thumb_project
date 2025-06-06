package com.shousi.thumb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shousi.thumb.mapper")
public class ThumbProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThumbProjectApplication.class, args);
    }

}
