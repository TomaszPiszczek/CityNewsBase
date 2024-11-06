package com.example.CityNewsBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.example.*")
@EntityScan("com.example.*")
@EnableScheduling
public class CityNewsBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityNewsBaseApplication.class, args);
	}

}
