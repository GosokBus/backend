package com.example.demo.post;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Post {
    @DocumentId
    private String 번호;
    private String 제목;
    private String 글;
}