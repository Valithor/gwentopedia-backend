package com.gwentopedia;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = { 
		GwentopediaBackendApplication.class,
		Jsr310JpaConverters.class 
})
public class GwentopediaBackendApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GwentopediaBackendApplication.class, args);
	}

}
