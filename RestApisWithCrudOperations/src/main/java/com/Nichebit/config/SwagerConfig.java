package com.Nichebit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwagerConfig {
	
	@Bean
	public OpenAPI getApiDoc()
	{
		//below concept called method chaining
		return new OpenAPI().info(new Info().title("Book Crud operations").
				description("this Document will gives info about the Book operatons and methods ")
				.version("1.0")
				.contact(new Contact().name("Devloped By Ramu").email("lakavathRamu123@nichebit.com")))
				.externalDocs(new ExternalDocumentation());
						
	}

}
