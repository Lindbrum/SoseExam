package it.univaq.dandd.flight_rest_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class RESTServiceFlights {

	public static void main(String[] args) {
		SpringApplication.run(RESTServiceFlights.class, args);
	}
	
	//Bean customizing the API specification (integrates with controller and operation level annotations as well as the automatically generated validation info on entity classes)
	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		 return new OpenAPI()
				 	//Components (objects re-usable elsewhere in the document, have no effect until referenced)
			        .components(new Components()
			        		//Security
			        		//.addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
			        		//Parameters
			        		//.addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1"))
			        		//Headers
			        		//.addHeaders("myHeader2", new Header().description("myHeader2 header").schema(new StringSchema()))
			        		//(...)
			        )
			        //info
			        .info(new Info()
				        .title("Flight routes API")
				        .version(appVersion)
				        .description("This is an API REST providing data about flight routes.")
				        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
				        );
	}
	
	//Enable CORS for gateway API tests (swagger-ui)
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/flights/**").allowedOrigins("http://localhost:8080");
			}
		};
	}
}