package com.example.demo.googlesheets;

import com.example.demo.userinfo.PasswordUpdateRequest;
import com.example.demo.userinfo.UserInfo;
import com.example.demo.userinfo.UserInfoService;
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

    private final GoogleSheetsService googleSheetsService;
    private final UserInfoService userInfoService;

    private static final String SPREADSHEET_ID = "1TKfJWX6l2ql2ReHfXYdbp5AXto2eGrTTarCxHW9LniY"; // 1. 기존에 스프레스 시트id를 복사해둔곳을 여기에 저장해둔다.
    private static final String RANGE = "구성원정보!I10:I16"; // 2. 작성할 행을 입력

    @PostMapping("/profile/{userId}/updatePassword")
    public ResponseEntity<String> updatePasswordToSheet(@PathVariable String userId, @RequestBody  PasswordUpdateRequest passwordUpdateRequest) {
        try {

            // 1. userId로 사번 찾기
            String 사번 = googleSheetsService.findSsnByUserId(SPREADSHEET_ID, "구성원정보!B10:B16", userId);
            if (사번 == null) {
                return ResponseEntity.badRequest().body("Invalid userId: " + userId);
            }

            // 2. 사번으로 행 계산
            String range = googleSheetsService.calculatePasswordCellRange(SPREADSHEET_ID, "구성원정보!B10:B16", 사번);

            if (range == null) {
                return ResponseEntity.badRequest().body("No matching row found for ssn: " + 사번);
            }

            // 3. 새로운 비밀번호로 업데이트
            List<List<Object>> values = List.of(Collections.singletonList(passwordUpdateRequest.getNewPassword()));
            googleSheetsService.writeToSheet(SPREADSHEET_ID, range, values);

            return ResponseEntity.ok("Password updated successfully for userId: " + userId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to update password: " + e.getMessage());
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
