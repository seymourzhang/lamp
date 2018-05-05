package com.on;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ImportResource(locations = {"classpath:spring/spring.xml"})
public class OnController {
	public static void main(String[] args) {
		SpringApplication.run(OnController.class, args);
	}
}
