package com.darkthor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class UserManagementApllication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementApllication.class, args);
    }
}
