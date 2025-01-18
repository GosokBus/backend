package com.example.demo.quest.partquest;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartQuest {

    @PropertyName("부여 경험치")
    private String rewardExp;
    @PropertyName("주차")
    private String week;
    @PropertyName("비고")
    private String note;
}
