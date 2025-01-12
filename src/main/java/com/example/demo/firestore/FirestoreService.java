package com.example.demo.firestore;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FirestoreService {

    private final Firestore firestore;

    public FirestoreService(Firestore firestore) {
        this.firestore = firestore;
    }

    //
    public void saveToFirestore(String collectionName, List<List<Object>> data) {

        // 첫 번째 행을 키로 사용 (헤더)
        List<Object> headers = data.getFirst();

        for (int i = 1; i < data.size(); i++) {
            List<Object> row = data.get(i);
            Map<String, Object> documentData = new HashMap<>();

            for (int j = 0; j < row.size(); j++) {
                documentData.put(headers.get(j).toString(), row.get(j));
            }

            firestore.collection(collectionName)
                    .document("row" + i)
                    .set(documentData);
        }
    }


}
