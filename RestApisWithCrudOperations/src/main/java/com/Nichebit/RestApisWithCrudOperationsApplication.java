package com.Nichebit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SpringBootApplication
@RestController
public class RestApisWithCrudOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApisWithCrudOperationsApplication.class, args);
		System.out.println("app started");
	}
	
    @GetMapping("/")
    public String GetWelcomeMessage() {
        return "welcome to Ec2 Cloud";
    }
      
}
