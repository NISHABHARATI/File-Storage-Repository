package com.example.demo.filemanager.controller;
import com.example.demo.filemanager.entity.User;
import com.example.demo.filemanager.model.ConstantValue;
import com.example.demo.filemanager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    ConstantValue constantValue;
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Map<String,Object> user, HttpServletRequest request) {
//        Map<String, Object> response = new HashMap<>();
//
//        String email = user.get("email").toString();
//        String password = user.get("password").toString();
//        String loggedInUser = userService.loginUser(email, password);
//        Long userId = userService.getUserIdByEmail(email);
////        System.out.println("User ID: "+ userId);
//        System.out.println("hello" + userId);
//        HttpSession session = request.getSession(true);
//        if (Boolean.TRUE.equals(constantValue.Validation)) {  // Safe null-check for Boolean
//            session.setAttribute("userId", userId);
//            System.out.println("Successful login!!");
//            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(loggedInUser,HttpStatus.UNAUTHORIZED);
//        }
//
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> user, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        String email = user.get("email").toString();
        String password = user.get("password").toString();

        // Attempt to login the user
        String loggedInUser = userService.loginUser(email, password);
        Long userId = userService.getUserIdByEmail(email);

        HttpSession session = request.getSession(true);

        // Check if login is successful
        if (Boolean.TRUE.equals(constantValue.Validation)) {  // Safe null-check for Boolean
            session.setAttribute("userId", userId);

            // Fetch user details (you may need to implement this method in your UserService)
            User userDetails = userService.getUserById(userId); // Replace with actual method to fetch user details

            // Prepare response with token and user details
            response.put("status", "success");
            response.put("token", loggedInUser); // Assuming loggedInUser is the token
            response.put("user", userDetails); // Assuming this is the user details object

            System.out.println("Successful login!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("status", "error");
            response.put("message", loggedInUser); // Error message
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


}


