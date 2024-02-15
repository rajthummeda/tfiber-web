package com.otsi.tfiberweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TfiberWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TfiberWebApplication.class, args);
	}

}