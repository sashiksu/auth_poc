package com.example.auth.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private RolesSeeder rolesSeeder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            rolesSeeder.seed();
        };
    }
}