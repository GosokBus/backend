package com.example.demo.post;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
public class Post {
    @DocumentId
    @PropertyName("번호")
    private String id;
    @PropertyName("제목")
    private String title;
    @PropertyName("글")
    private String content;
}