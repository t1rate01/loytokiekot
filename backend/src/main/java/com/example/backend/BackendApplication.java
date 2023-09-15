package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing  // mahdollistaa @CreatedDate ja @LastModifiedDate annotaatiot
@EnableScheduling
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

		@Bean
	public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000") // allow the React frontend
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                    .allowedHeaders("Authorization", "content-type")
                    .exposedHeaders("Authorization");
        }
    };
}
}



// muista: kirjainkoosta riippumaton vertailu tarkistusfunktioon
// seuraavaksi funktio / toiminto joka tarkistaa kiekkoa lisätessä sen avainsanat heti ja vertaa niitä käyttäjien tallentamiin avainsanoihin