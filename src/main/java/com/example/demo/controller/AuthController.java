//package com.example.demo.controller;
//
//import com.example.demo.service.FirestoreReadService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.ExecutionException;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final FirestoreReadService firestoreReadService;
//
//    public AuthController(FirestoreReadService firestoreReadService) {
//        this.firestoreReadService = firestoreReadService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String userId, @RequestParam String basicPassword) {
//        try {
//            boolean isAuthenticated = firestoreReadService.authenticateUser(userId, basicPassword);
//
//            if (isAuthenticated) {
//                return ResponseEntity.ok("Login successful!");
//            } else {
//                return ResponseEntity.status(401).body("Invalid username or password.");
//            }
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("An error occurred while processing the login.");
//        }
//    }
//
//}
