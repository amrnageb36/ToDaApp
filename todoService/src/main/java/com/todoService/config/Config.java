package com.todoService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class Config {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ToDo Service API")
                        .version("1.0")
                        .description("API documentation for the ToDo Service")
                        //.contact(new Contact()
                        //        .name("Your Name")
                          //      .email("your.email@example.com")
                          //      .url("https://your-website.com")

                );
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
