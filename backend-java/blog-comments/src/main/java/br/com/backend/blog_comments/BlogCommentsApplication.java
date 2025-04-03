package br.com.backend.blog_comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BlogCommentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogCommentsApplication.class, args);
	}

}
