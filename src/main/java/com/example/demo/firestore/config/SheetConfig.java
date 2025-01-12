package com.example.demo.firestore.config;

import java.util.List;

public class SheetConfig {
    private String spreadsheetId;
    private List<RangeConfig> ranges;

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }

    public List<RangeConfig> getRanges() {
        return ranges;
    }

    public void setRanges(List<RangeConfig> ranges) {
        this.ranges = ranges;
    }
}