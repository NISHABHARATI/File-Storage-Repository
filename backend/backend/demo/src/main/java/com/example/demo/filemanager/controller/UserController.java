package com.example.demo.filemanager.controller;

import com.example.demo.filemanager.entity.User;
import com.example.demo.filemanager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/details")
    public ResponseEntity<User> getUserDetails(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("Session is null, user not logged in.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = (Long) session.getAttribute("userId");  // Retrieve userId from session

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // If userId is null, user is not logged in
        }

        // Fetch user details by userId
        User user = userService.getUserById(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // If user not found, return 404
        }

        return new ResponseEntity<>(user, HttpStatus.OK);  // Return user details
    }
}

