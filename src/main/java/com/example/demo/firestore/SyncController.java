package com.example.demo.firestore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SyncController {

    private final SpreadsheetToFirestoreService syncService;

    public SyncController(SpreadsheetToFirestoreService syncService) {
        this.syncService = syncService;
    }

    @GetMapping("/sync")
    public String sync(@RequestParam String spreadsheetId,
                       @RequestParam String range,
                       @RequestParam String collectionName) {
        try {
            syncService.syncSpreadsheetToFirestore(spreadsheetId, range, collectionName);
            return "Sync completed successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to sync: " + e.getMessage();
        }
    }

}
