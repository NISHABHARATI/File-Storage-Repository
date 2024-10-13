package com.example.demo.filemanager.service;
import com.example.demo.filemanager.entity.User;
import com.example.demo.filemanager.model.ConstantValue;
import com.example.demo.filemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {

    @Autowired
    ConstantValue constantValue;
    @Autowired
    UserRepository userRepository;


    public User registerUser(User user) {
        // Check if the user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this username or email");
        }
        // Save the new user
        return userRepository.save(user);
    }

    public String loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            constantValue.UserFound = true;
            User user = userOpt.get();

            // Password check (consider using hashed passwords in a real application)
            if (user.getPassword().equals(password)) {
                constantValue.Validation = true;
                return "Login successfully";  // Return the logged-in user if the validation passes
            } else {
                constantValue.Validation = false;
                return "invalid user";
            }
        } else {
            constantValue.UserFound = false;
            constantValue.Validation = false;
            return "user not found";
        }
        // Return null if login fails
    }

    public User getUserById(Long userId) {
    return userRepository.findById(userId).orElse(null);  // Fetch user details by userId
    }
    public Long getUserIdByEmail(String email) {
        // Use the repository method to find user by email
        Optional<User> userOptional = userRepository.findByEmail(email);

        // Check if user exists, if not throw an exception or return null
        if (userOptional.isPresent()) {
            long mid = userOptional.get().getUserId();
            System.out.println(mid);
            return mid;// Assuming User entity has getUserId method
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }



}

