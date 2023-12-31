package com.example.payrollsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class PayrollSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayrollSystemApplication.class, args);
    }

}
