package com.example.day0706;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Day0706Application {

	public static void main(String[] args) {
		SpringApplication.run(Day0706Application.class, args);
	}

}
