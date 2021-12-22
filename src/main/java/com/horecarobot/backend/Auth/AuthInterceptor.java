package com.horecarobot.backend.Auth;

import com.horecarobot.backend.Exceptions.InvalidTokenException;
import com.horecarobot.backend.Exceptions.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    @Autowired
    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnAuthorizedException, InvalidTokenException {
        // [IMPORTANT] The following code checks if an incoming request, with a valid token, can pass or not.
        String currentURL = request.getRequestURI();

/*        System.out.println("---");

        Enumeration<String> headerNames = request.getHeaderNames();
        if(headerNames != null) {
            while(headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                System.out.println(headerName + " | " + request.getHeader(headerName));
            }
        }

        System.out.println("---");*/

        if(currentURL.equals("/api/v1/employee") && List.of("GET", "OPTIONS").contains(request.getMethod())) {
            return true;
        }

        if(currentURL.matches("\\/api\\/v1\\/employee\\/[^\\/]+") && List.of("GET", "OPTIONS").contains(request.getMethod())) {
            return true;
        }

        // Lets the requests to /employee/:ID/login - /employee/:ID/validate and /employee/:ID/refresh pass
        if(currentURL.matches("\\/api\\/v1\\/employee\\/[^\\/]+\\/(login|validate|refresh)")) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || authHeader.length() == 0 || !authHeader.contains("Bearer")) {
            throw new UnAuthorizedException("Missing authorization header.");
        }

        String jwt = authHeader.replaceAll("Bearer:?\\s*", "");
        if(jwt.length() == 0) {
            throw new UnAuthorizedException("Missing token header.");
        }

        if(!this.authService.tokenIsValid(jwt)) {
            throw new InvalidTokenException("Given JWT is not valid.");
        }

        return true;
    }
}
