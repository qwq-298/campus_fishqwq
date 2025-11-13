package com.compusfishqwq.compus_fishqwq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CompusFishqwqApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompusFishqwqApplication.class, args);
	}

}
