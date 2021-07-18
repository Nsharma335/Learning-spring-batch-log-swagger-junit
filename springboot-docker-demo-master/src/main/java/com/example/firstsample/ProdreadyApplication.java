package com.example.firstsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "com.example.firstsample", "com.example.firstsample.domain",
		"com.example.firstsample.web", "com.example.firstsample.service", "com.example.firstsample.repo" })
@SpringBootApplication
public class ProdreadyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdreadyApplication.class, args);
	}

}
