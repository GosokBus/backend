package com.example.demo.userinfo;

import java.util.Arrays;

public enum Department {
    EUMSUNG_1("음성 1센터"),
    EUMSUNG_2("음성 2센터"),
    YONGIN("용인백암센터"),
    NAMYANGJU("남양주센터"),
    PAJU("파주센터"),
    BUSINESS_PLANNING("사업기획팀"),
    GROWTH("그로스팀"),
    CX("CX팀");

    private final String name;

    Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Department of(String name) {
        return Arrays.stream(values())
                .filter(department -> department.getName().equals(name))
                .findFirst()
                .orElse(null); // 또는 예외를 던지도록 수정
    }
}
