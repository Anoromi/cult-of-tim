package com.cult_of_tim.auth.cultoftimauth;

import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.cult_of_tim.auth.cultoftimauth.util.UserCheckerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration

@EnableJpaRepositories("com.cult_of_tim.auth.cultoftimauth")
@EntityScan("com.cult_of_tim.auth.cultoftimauth")
public class CultOfTimAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultOfTimAuthApplication.class, args);
	}



}
