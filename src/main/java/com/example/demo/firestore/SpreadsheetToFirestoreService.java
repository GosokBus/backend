package com.example.demo.firestore;

import com.example.demo.GoogleSheetsService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpreadsheetToFirestoreService {

    private final GoogleSheetsService googleSheetsService;
    private final FirestoreService firestoreService;

    public SpreadsheetToFirestoreService(GoogleSheetsService googleSheetsService, FirestoreService firestoreService) {
        this.googleSheetsService = googleSheetsService;
        this.firestoreService = firestoreService;
    }

    public void syncSpreadsheetToFirestore(String spreadsheetId, String range, String collectionName) throws IOException {
        List<List<Object>> data = googleSheetsService.readFromSheet(spreadsheetId, range);
        firestoreService.saveToFirestore(collectionName, data);
    }

}
