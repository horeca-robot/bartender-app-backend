package com.horecarobot.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@ComponentScan({"edu.fontys.horecarobot.databaselibrary.*","com.horecarobot.backend.*"})
@EnableJpaRepositories({"edu.fontys.horecarobot.databaselibrary.*","com.horecarobot.backend.*"})
@Import(SecurityConfig.class)
@EntityScan("edu.fontys.horecarobot.databaselibrary.*")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
