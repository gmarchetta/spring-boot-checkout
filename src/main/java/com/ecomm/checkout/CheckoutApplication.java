package com.ecomm.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CheckoutApplication {

	@RequestMapping("/")
	public String helloWorld(){
		return "hello world!";
	}

	public static void main(String[] args) {
		SpringApplication.run(CheckoutApplication.class, args);
	}

}
