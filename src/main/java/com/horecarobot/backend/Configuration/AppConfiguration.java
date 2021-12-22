package com.horecarobot.backend.Configuration;

import com.horecarobot.backend.Auth.AuthInterceptor;
import com.horecarobot.backend.Auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {
    private final AuthService authService;

    @Autowired
    public AppConfiguration(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("http://localhost:8081", "http://127.0.0.1:8081", "https://horecarobot-frontend.azurewebsites.net/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(this.authService));
    }
}
