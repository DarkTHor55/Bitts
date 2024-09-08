package com.darkthor.Filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    // List of API endpoints that do not require JWT validation
    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/user/signup",
            "/api/v1/user/login",
            "/api/v1/user/otp-creation",
            "/api/v1/user/otp-validation"
    );

    // Predicate to check if a route needs JWT validation
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    // Matches only exact paths for the given routes
                    .noneMatch(uri -> request.getURI().getPath().equalsIgnoreCase(uri));

}
