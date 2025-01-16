package com.example.demo.userinfo;

import lombok.Getter;
@Getter
public enum Department {

    EUMSUNG_1_1("음성 1센터", "음성1센터-리더부여퀘스트","음성1센터1그룹-직무별퀘스트"),
    EUMSUNG_1_2("음성 1센터", "음성1센터-리더부여퀘스트","음성1센터2그룹-직무별퀘스트"),
    EUMSUNG_2("음성 2센터", "음성2센터-리더부여퀘스트", "음성2센터-직무별퀘스트"),
    YONGIN("용인백암센터", "용인백암센터-리더부여퀘스트", "용인백암센터-직무별퀘스트"),
    NAMYANGJU("남양주센터", "남양주센터-리더부여퀘스트", "남양주센터-직무별퀘스트"),
    PAJU("파주센터", "파주센터-리더부여퀘스트", "파주센터-직무별퀘스트"),
    BUSINESS_PLANNING("사업기획팀", "사업기획팀-리더부여퀘스트", "사업기획팀-직무별퀘스트"),
    GROWTH("그로스팀", "그로스팀-리더부여퀘스트", "그로스팀-직무별퀘스트"),
    CX("CX팀", "CX팀-리더부여퀘스트", "CX팀-직무별퀘스트");

    private final String name;
    private final String leaderQuest;
    private final String partQuest;

    Department(String name, String leaderQuest, String partQuest) {
        this.name = name;
        this.leaderQuest = leaderQuest;
        this.partQuest = partQuest;
    }

    public static Department fromName(String name) {
        for (Department dept : Department.values()) {
            if (dept.getName().equals(name)) {
                return dept;
            }
        }
        return null; // 또는 예외 처리
    }
}
