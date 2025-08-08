package com.ABG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AdityaBirlaGroupApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdityaBirlaGroupApplication.class, args);
	}

}
