package com.burak.accounts;

import com.burak.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication//auto configuration and auto scan all beans
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")//activate jpa auditing and leverage the bean auditAwareImpl
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
  info = @Info(
    title = "Accounts Microservice REST API Documentation",
    description = "Accounts Microservice REST API Description",
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
    description = "Accounts Microservice REST API Documentation",
    url = "http://burakerdogan/swagger-ui/index.html"
  )
)
public class AccountsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountsApplication.class, args);
  }

}
