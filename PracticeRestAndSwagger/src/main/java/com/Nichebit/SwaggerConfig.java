package com.Nichebit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;



@Configuration
public class SwaggerConfig  {
	
	
	
	/*
	 * @Bean public Docket api() throws Exception { return new
	 * Docket(DocumentationType.SWAGGER_2) .select()
	 * .apis(RequestHandlerSelectors.basePackage("com.Nichebit.rest"))
	 * .paths(PathSelectors.any()) .build(); }
	 */
	
	@Bean
	public OpenAPI getapi()
	{
		
		return new OpenAPI()
				.info(new Info().title("Air Ticket By Air india")
						.description("this is Air ticket booking api devloped by Nichebit")
						.version("1.0")
						.contact(new Contact()
						.name("Ramu").email("ramu.nichebit@gmail.com")))
				          .externalDocs(new ExternalDocumentation());
	}

	

}
