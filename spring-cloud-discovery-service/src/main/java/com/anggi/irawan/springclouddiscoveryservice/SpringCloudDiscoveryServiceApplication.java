package com.anggi.irawan.springclouddiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDiscoveryServiceApplication.class, args);
    }

}
