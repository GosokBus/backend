package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.checkerframework.framework.qual.RequiresQualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/google")
@RequiredArgsConstructor
public class GoogleSheetsController {

    private final com.example.demo.GoogleSheetsService googleSheetsService;

    private static final String SPREADSHEET_ID = "1TKfJWX6l2ql2ReHfXYdbp5AXto2eGrTTarCxHW9LniY"; // 1. 기존에 스프레스 시트id를 복사해둔곳을 여기에 저장해둔다.
    private static final String RANGE = "연습용2!C11"; // 2. 작성할 행을 입력

    @PostMapping("/write")
    public ResponseEntity<String> writeToSheet(@RequestParam String word) {
        try {
            // 3. 데이터를 스프레드시트에 쓰기 위해 전달되는 형식
            // 행과 열에 데이터를 매핑하기 위함
            List<List<Object>> values = List.of(Collections.singletonList(word));

            // 4. 서비스 로직 호출
            googleSheetsService.writeToSheet(SPREADSHEET_ID, RANGE, values);

            return ResponseEntity.ok("Data written successfully to the spreadsheet: " + word);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to write data: " + e.getMessage());
        }
    }

    @GetMapping("/read")
    public ResponseEntity<?> readFromSheet(@RequestParam String range) {
        try {
            // range를 동적으로 받아서 Service에 전달
            List<List<Object>> result = googleSheetsService.readFromSheet(SPREADSHEET_ID, range);

            if (result == null || result.isEmpty()) {
                return ResponseEntity.ok("No data found in the given range.");
            }
            return ResponseEntity.ok(result);  // JSON 형태로 반환
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Failed to read data: " + e.getMessage());
        }



    }

}
