package com.example.demo.firestore.config;

import java.util.List;

public class SyncConfig {
    private List<SheetConfig> sheets;

    public List<SheetConfig> getSheets() {
        return sheets;
    }

    public void setSheets(List<SheetConfig> sheets) {
        this.sheets = sheets;
    }
}