package com.example.auth.service;

import com.example.auth.dto.request.SignInRequest;
import com.example.auth.dto.request.SignUpRequest;
import com.example.auth.entity.UserEntity;
import com.example.auth.repository.UserRepository;
import com.example.auth.utils.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    public UserEntity signUp(SignUpRequest signupPayload) {
        try {
            String username = signupPayload.getUsername();
            String password = signupPayload.getPassword();
            String firstName = signupPayload.getFirstName();
            String lastName = signupPayload.getLastName();

            // Check if username already exists
            Optional<UserEntity> existingUser = Optional.ofNullable(userRepository.findByUsername(username));
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("Username already exists.");
            }
            // Optionally hash password here
            String hashedPassword = passwordEncoder.encode(password);
            UserEntity newUser = new UserEntity(username, hashedPassword, firstName, lastName);
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }

    public UserEntity signIn(SignInRequest signInPayload) {
        try {
            String username = signInPayload.getUsername();
            String password = signInPayload.getPassword();

            // Check if username already exists
            Optional<UserEntity> existingUser = Optional.ofNullable(userRepository.findByUsername(username));
            if (existingUser.isEmpty()) {
                throw new IllegalArgumentException("Please create a new user to start using our service.");
            }

            // Retrieve the User object from the Optional
            UserEntity user = existingUser.get();

            // Compare the stored hashed password with the incoming plain password
            if (!passwordEncoder.matches(password, user.getHashPassword())) {
                throw new IllegalArgumentException("Invalid credentials provided try reset password.");
            }

            return existingUser.get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to signing user: " + e.getMessage(), e);
        }
    }
}
