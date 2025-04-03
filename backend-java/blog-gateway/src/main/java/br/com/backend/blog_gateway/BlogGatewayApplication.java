package br.com.backend.blog_gateway;

import br.com.backend.blog_gateway.filter.AuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BlogGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder,AuthenticationFilter authenticationFilter) {
		return routeLocatorBuilder.routes()
				.route(r-> r.path("/article/**","/article")
						.filters(f-> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
						.uri("lb://msarticle"))
				.route(r-> r.path("/auth/**","/auth").uri("lb://mssecurity"))
				.route(r-> r.path("/comments").uri("lb://mscomments"))
				.build();
	}

}
