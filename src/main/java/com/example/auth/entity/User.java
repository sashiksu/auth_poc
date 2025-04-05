package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "is_expired", nullable = false)
    private Boolean isExpired = false;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked= false;

    @Column(name = "is_credentials_expired", nullable = false)
    private Boolean isCredentialsExpired= false;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    // Constructors
    public User() {
    }

    public User(String username, String hashPassword, String firstName, String lastName) {
        this.username = username;
        this.hashPassword = hashPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}