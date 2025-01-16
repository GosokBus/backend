package com.example.demo.firestore;

import com.example.demo.firestore.config.SyncConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class RangeConfigLoader {

    private final ObjectMapper objectMapper;

    public RangeConfigLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * JSON 파일에서 범위와 컬렉션 정보를 로드
     *
     * @param filePath JSON 파일 경로
     * @return 설정 데이터
     * @throws IOException 파일 읽기 실패 시
     */
    public SyncConfig loadRangeConfig(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), SyncConfig.class);
    }
}