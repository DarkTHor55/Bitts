package com.darkthor.Filter;

import com.darkthor.Util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouterValidator routeValidator;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {
        // Configuration properties can be added here if needed
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            // Check if the request requires authentication
            if (routeValidator.isSecured.test(request)) {

                // Check if the Authorization header is present
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "Missing Authorization Header");
                }

                // Extract the token from the Authorization header
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    return onError(exchange, "Invalid Authorization Header");
                }

                try {
                    // Validate the JWT token
                    jwtUtils.validateToken(authHeader);
                    String role = jwtUtils.extractRole(authHeader);

                    // Inject the role into the request headers for downstream services
                    request = exchange.getRequest()
                            .mutate()
                            .header("loginUserByRole", role)
                            .build();

                    // Role-based access control
                    if ("user".equalsIgnoreCase(role)) {
                        if (!isUserRoleAllowed(path)) {
                            return onError(exchange, "User role does not have access to this service");
                        }
                    } else if ("hr".equalsIgnoreCase(role)) {
                        if (!isHrRoleAllowed(path)) {
                            return onError(exchange, "HR role does not have access to this service");
                        }
                    } else if ("accountant".equalsIgnoreCase(role)) {
                        if (!isAccountantRoleAllowed(path)) {
                            return onError(exchange, "Accountant role does not have access to this service");
                        }
                    } else if ("sales".equalsIgnoreCase(role)) {
                        if (!isSalesRoleAllowed(path)) {
                            return onError(exchange, "Sales role does not have access to this service");
                        }
                    } else if ("administrator".equalsIgnoreCase(role)) {
                        if (!isAdministratorRoleAllowed(path)) {
                            return onError(exchange, "Administrator role does not have access to this service");
                        }
                    }

                    // Continue with the request
                    return chain.filter(exchange.mutate().request(request).build());

                } catch (Exception e) {
                    return onError(exchange, "Invalid token");
                }
            }

            // If the route is not secured, continue with the request
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error) {
        System.out.println(error);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    // Methods to check role-based access

    private boolean isUserRoleAllowed(String path) {
        return path.matches("/api/v1/users/login(/.*)?") ||
                path.matches("/api/v1/users/signup(/.*)?") ||
                path.matches("/api/v1/users/validate(/.*)?");
    }

    private boolean isHrRoleAllowed(String path) {
        return path.matches("/api/v1/users/payroll(/.*)?") ||
                path.matches("/api/v1/users/payroll/create(/.*)?");
    }

    private boolean isAccountantRoleAllowed(String path) {
        return path.matches("/api/v1/users/payroll(/.*)?") ||
                path.matches("/api/v1/users/bill(/.*)?");
    }

    private boolean isSalesRoleAllowed(String path) {
        return path.matches("/api/v1/users/customer(/.*)?") ||
                path.matches("/api/v1/users/bill(/.*)?");
    }

    private boolean isAdministratorRoleAllowed(String path) {
        return path.matches("/api/v1/users/management(/.*)?");
    }

}