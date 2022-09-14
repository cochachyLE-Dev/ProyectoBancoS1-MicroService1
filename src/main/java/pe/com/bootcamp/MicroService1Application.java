package pe.com.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
@EnableWebFlux
//SonarQube
public class MicroService1Application {
	public static void main(String[] args) {
		SpringApplication.run(MicroService1Application.class, args);
	}
}