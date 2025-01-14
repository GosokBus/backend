package com.example.demo.expthisyear;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpThisYear {

    @DocumentId
    @PropertyName("사번")
    private String userId;

    @PropertyName("2024년 획득한 총 경험치")
    private String exp;
    @PropertyName("레벨")
    private String level;
    @PropertyName("리더부여 퀘스트")
    private String leaderQuest;
    @PropertyName("상반기 인사평가")
    private String fPersonalEval;
    @PropertyName("소속")
    private String part;
    @PropertyName("이름")
    private String name;
    @PropertyName("전사 프로젝트")
    private String cProject;
    @PropertyName("직무그룹")
    private String group;
    @PropertyName("직무별 퀘스트")
    private String partQuest;
    @PropertyName("하반기 인사평가")
    private String sPersonalEval;
}
