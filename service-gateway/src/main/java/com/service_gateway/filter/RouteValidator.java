package com.service_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/blog/v3/api-docs",
            "/comment/v3/api-docs",
            "/security/v3/api-docs",
            "/user/loginReq",
            "/user/register",
//            "/blog/save/**",
            "/eureka"

    );
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
