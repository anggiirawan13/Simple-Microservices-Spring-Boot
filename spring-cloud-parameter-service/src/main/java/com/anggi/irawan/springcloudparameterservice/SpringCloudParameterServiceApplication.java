package com.anggi.irawan.springcloudparameterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SpringCloudParameterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudParameterServiceApplication.class, args);
    }

}
