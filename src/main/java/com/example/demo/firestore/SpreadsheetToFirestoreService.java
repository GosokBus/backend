package com.example.demo.firestore;

import com.example.demo.googlesheets.GoogleSheetsService;
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
        // 스프레드시트에서 값 받아온다
        // data 에는 스프레드시트 Id, 범위, 값이 모두 저장 돼있다.
        List<List<Object>> data = googleSheetsService.readFromSheet(spreadsheetId, range);
        // data 를 firestore 의 특정 위치에 저장해준다
        firestoreService.saveToFirestore(collectionName, data);
    }

}
