package com.anggi.irawan.springcloudcifservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition
public class SpringCloudCifServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudCifServiceApplication.class, args);
    }

}
