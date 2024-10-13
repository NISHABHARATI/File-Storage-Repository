package com.example.demo.filemanager.service;

import com.example.demo.filemanager.entity.User;
import com.example.demo.filemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service  // Mark this as a service so Spring can manage it as a bean
public class ProfileService {

    @Autowired
    private UserRepository userRepository;  // Remove static

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);  // No need for static
    }

    public User updateUserProfile(Long userId, User updatedUser) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());  // Ensure unique email
            return userRepository.save(user);
        }
        return null;
    }
}

