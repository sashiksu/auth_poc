package com.example.auth.seeder;

import com.example.auth.entity.Role;
import com.example.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolesSeeder {

    @Autowired
    private RoleRepository repository;

    public void seed() {
        List<Role> defaultRoles = List.of(
                new Role(null, "SUPER_ADMIN"), // Role ID is generated, so null here
                new Role(null, "ADMIN"),
                new Role(null, "OWNER"),
                new Role(null, "MANAGER"),
                new Role(null, "CASHIER"),
                new Role(null, "ACCOUNTANT")
        );

        for (Role role : defaultRoles) {
            repository.findByName(role.getName())
                    .ifPresentOrElse(existing -> {
                                // Role exists, do nothing (or log a message)
                            },
                            // Role does not exist, so insert it
                            () -> repository.save(role));
        }
    }

}
