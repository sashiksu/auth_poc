package com.example.auth.seeder;

import com.example.auth.entity.RoleEntity;
import com.example.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolesSeeder {

    @Autowired
    private RoleRepository repository;

    public void seed() {
        List<RoleEntity> defaultRoles = List.of(
                new RoleEntity(null, "SUPER_ADMIN"), // Role ID is generated, so null here
                new RoleEntity(null, "ADMIN"),
                new RoleEntity(null, "OWNER"),
                new RoleEntity(null, "MANAGER"),
                new RoleEntity(null, "CASHIER"),
                new RoleEntity(null, "ACCOUNTANT")
        );

        for (RoleEntity role : defaultRoles) {
            repository.findByName(role.getName())
                    .ifPresentOrElse(existing -> {
                                // Role exists, do nothing (or log a message)
                            },
                            // Role does not exist, so insert it
                            () -> repository.save(role));
        }
    }

}
