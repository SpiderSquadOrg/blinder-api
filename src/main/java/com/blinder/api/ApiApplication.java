package com.blinder.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.ailef.snapadmin.external.SnapAdminAutoConfiguration;

@ImportAutoConfiguration(SnapAdminAutoConfiguration.class)
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
