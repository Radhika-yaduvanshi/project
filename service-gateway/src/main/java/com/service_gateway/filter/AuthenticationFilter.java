package com.service_gateway.filter;

import com.service_gateway.jwtutil.jwtUtil;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator validator;

//    @Autowired
//    private RestTemplate template;

    @Autowired
    private jwtUtil jwtUtil;

//    @Autowired
//    private AESUtil AESUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                String path = exchange.getRequest().getURI().getPath();
                // Bypass authentication for Swagger-related paths
//                if (path.contains("/v3/api-docs") || path.contains("/swagger-ui.html")) {
//                    return chain.filter(exchange);  // Skip the filter for Swagger
//                }
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
//                    //REST call to AUTH service
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
                    jwtUtil.verifyTocken(authHeader);

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }



            //code for dcryption=====


            // Decryption logic
//            String path = exchange.getRequest().getPath().toString();
//            // Skip decryption for unencrypted routes
//            if (path.contains("/loginReq") || path.contains("/register")) {
//                return chain.filter(exchange);
//            }

            // Decryption logic
            String path = exchange.getRequest().getPath().toString();
            // Skip decryption for unencrypted routes
            if (path.contains("/loginReq") || path.contains("/register")) {
                return chain.filter(exchange);
            }

            // Handle decryption only for POST or PUT requests
            if (exchange.getRequest().getMethod() == HttpMethod.POST || exchange.getRequest().getMethod() == HttpMethod.PUT) {
                return DataBufferUtils.join(exchange.getRequest().getBody())
                        .flatMap(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer);

                            String encryptedBody = new String(bytes, StandardCharsets.UTF_8);
                            System.err.println("Encrypted Body: " + encryptedBody);
                            String decryptedBody;

                            try {
                                // Decrypting the body using AESUtil with the correct parameters
                                decryptedBody = AESUtil.decrypt(encryptedBody, "MySecretKey12345", "MySecretKey12345");
                                System.err.println("Decrypted Body: " + decryptedBody);
                            } catch (Exception e) {
                                System.err.println("Decryption failed: " + e.getMessage());
                                // If decryption fails, continue without modifying the body
                                return chain.filter(exchange);
                            }

                            // Create new body with decrypted data
                            byte[] newBody = decryptedBody.getBytes(StandardCharsets.UTF_8);
                            Flux<DataBuffer> bodyFlux = Flux.just(exchange.getResponse().bufferFactory().wrap(newBody));



                            // Mutate the request to set the decrypted body
                            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                                @Override
                                public Flux<DataBuffer> getBody() {
                                    return bodyFlux;
                                }
                            };

                            System.out.println("✅ Decrypted request body: " + decryptedBody);

                            return chain.filter(exchange.mutate().request(mutatedRequest).build());
                        });
            }


            //ending dcryption code



            return chain.filter(exchange);
        });
    }

    public static class Config {

    }




}
