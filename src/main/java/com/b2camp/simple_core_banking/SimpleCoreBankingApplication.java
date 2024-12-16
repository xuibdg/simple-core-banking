package com.b2camp.simple_core_banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.b2camp.simple_core_banking")
public class SimpleCoreBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCoreBankingApplication.class, args);
	}

}
