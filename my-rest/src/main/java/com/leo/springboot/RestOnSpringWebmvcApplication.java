package com.leo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leo.springboot.enable.EnableEcho;

@SpringBootApplication
@EnableEcho(packages= {"com.leo.springboot.enable.dto"})
public class RestOnSpringWebmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestOnSpringWebmvcApplication.class, args);
	}
}
