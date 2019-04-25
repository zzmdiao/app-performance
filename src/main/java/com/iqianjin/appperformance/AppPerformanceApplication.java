package com.iqianjin.appperformance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.iqianjin.appperformance")
public class AppPerformanceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppPerformanceApplication.class, args);
    }
}
