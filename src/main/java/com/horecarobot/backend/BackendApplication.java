package com.horecarobot.backend;

import com.auth0.jwt.algorithms.Algorithm;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan({"edu.fontys.horecarobot.databaselibrary.*","com.horecarobot.backend.*"})
@EnableJpaRepositories({"edu.fontys.horecarobot.databaselibrary.*","com.horecarobot.backend.*"})
@Import(SecurityConfig.class)
@EntityScan("edu.fontys.horecarobot.databaselibrary.*")
public class BackendApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


//	public WebMvcConfigurer configureGlobalCORS()
//	{
//		return new WebMvcConfigurer()
//		{
//			@Override
//			public void addCorsMappings(CorsRegistry registry)
//			{
//				registry.addMapping("/api/**").allowedOrigins("http://localhost:8081", "http://127.0.0.1:8081", "https://rigs.bryanaafjes.nl", "https://rigs.bryanaafjes.nl:444");
//			}
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
