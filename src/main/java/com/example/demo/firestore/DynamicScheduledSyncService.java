package com.example.demo.firestore;

import com.example.demo.firestore.config.RangeConfig;
import com.example.demo.firestore.config.SheetConfig;
import com.example.demo.firestore.config.SyncConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class DynamicScheduledSyncService {

    private final RangeConfigLoader configLoader;
    private final SpreadsheetToFirestoreService syncService;

    public DynamicScheduledSyncService(RangeConfigLoader configLoader, SpreadsheetToFirestoreService syncService) {
        this.configLoader = configLoader;
        this.syncService = syncService;
    }

    @Scheduled(fixedRate = 20000) // 6초 간격으로 실행
    public void syncMultipleRanges() {
        try {
            // JSON 파일에서 설정 로드
            SyncConfig syncConfig = configLoader.loadRangeConfig("src/main/resources/sync-config.json");

            // 각 시트와 범위 처리
            for (SheetConfig sheet : syncConfig.getSheets()) {
                String spreadsheetId = sheet.getSpreadsheetId();

                for (RangeConfig range : sheet.getRanges()) {
                    String rangeValue = range.getRange();
                    String collectionName = range.getCollectionName();

                    // 동기화 수행
                    syncService.syncSpreadsheetToFirestore(spreadsheetId, rangeValue, collectionName);
                    System.out.println("Synced range " + rangeValue + " to collection " + collectionName);
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Sync failed: " + e.getMessage());
        }
    }
}