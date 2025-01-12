package com.example.demo.controller;

import com.example.demo.service.FirestoreReadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class FirestoreReadController {

    private final FirestoreReadService firestoreReadService;

    public FirestoreReadController(FirestoreReadService firestoreReadService) {
        this.firestoreReadService = firestoreReadService;
    }

    @GetMapping("/api/data")
    public List<Map<String, Object>> getCollectionData(@RequestParam String collectionName) {
        try {
            return firestoreReadService.getCollectionData(collectionName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch data from Firestore: " + e.getMessage());
        }
    }


}
