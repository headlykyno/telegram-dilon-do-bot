package com.headlyboy.tgdilondobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TgdilondobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TgdilondobotApplication.class, args);
    }

}
