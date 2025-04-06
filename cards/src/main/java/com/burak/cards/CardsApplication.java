package com.burak.cards;

import com.burak.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
@OpenAPIDefinition(
  info = @Info(
    title = "Cards microservice REST API Documentation",
    description = "Bank Cards microservice REST API Documentation",
    version = "v1",
    contact = @Contact(
      name = "Burak Erdogan",
      email = "burakerdogan749@gmail.com",
      url = "https://burakerdogan.netlify.app"
    ),
    license = @License(
      name = "Apache 2.0",
      url = "https://burakerdogan.netlify.app"
    )
  ),
  externalDocs = @ExternalDocumentation(
    description = "Bank Cards microservice REST API Documentation",
    url = "https://www.burakerdogan.com/swagger-ui.html"
  )
)
public class CardsApplication {

  public static void main(String[] args) {
    SpringApplication.run(CardsApplication.class, args);
  }

}
