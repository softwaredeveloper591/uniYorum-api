package com.enes.uniyorum_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.enes"})
@EntityScan(basePackages = {"com.enes"})
@EnableJpaRepositories(basePackages = {"com.enes"})
@SpringBootApplication(scanBasePackages = "com.enes")
public class UniyorumApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniyorumApiApplication.class, args);
	}

}
