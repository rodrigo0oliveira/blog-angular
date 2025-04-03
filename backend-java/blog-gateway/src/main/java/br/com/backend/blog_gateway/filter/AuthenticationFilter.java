package br.com.backend.blog_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory {


    private final RouteValidator routeValidator;

    private final JwtValidator jwtValidator;

    public AuthenticationFilter(RouteValidator routeValidator,JwtValidator jwtValidator) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtValidator = jwtValidator;
    }


    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){

                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing Authorization header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }

                String finalAuthHeader = authHeader;

                try {
                    boolean valid = jwtValidator.isValid(finalAuthHeader);
                    if(valid){
                        log.info("Valid Token");
                        return chain.filter(exchange);
                    }
                    else{
                        log.info("Invalid Token");
                        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
