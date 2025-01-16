package com.example.demo.post;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class PostService {

    private final Firestore firestore;

    public PostService(Firestore firestore) {
        this.firestore = firestore;
    }

    // 게시글 생성
    public Post createPost(Post post) throws Exception {
        // 카운터 값 가져오기 및 업데이트
        Transaction.Function<String> updateFunction = transaction -> {
            DocumentReference counterRef = firestore.collection("counters").document("postCounter");
            DocumentSnapshot snapshot = transaction.get(counterRef).get();

            long newNumber;
            if (!snapshot.exists()) {
                // 카운터 문서가 없으면 새로 생성
                newNumber = 0;
                transaction.set(counterRef, Map.of("count", newNumber));
            } else {
                newNumber = snapshot.getLong("count") + 1;
                transaction.update(counterRef, "count", newNumber);
            }

            String id = String.valueOf(newNumber);
            post.setId(id);
            DocumentReference postRef = firestore.collection("post").document(id);
            transaction.set(postRef, post);
            return id;
        };

        ApiFuture<String> transactionResult = firestore.runTransaction(updateFunction);

        String newId = null; // newId를 블록 외부에서 선언
        try {
            newId = transactionResult.get();
        } catch (InterruptedException | ExecutionException e) {
            // 예외 처리
            log.error("Error creating post", e);
            throw new Exception("Failed to create post", e);
        }

        return getPostById(newId);
    }

    // 모든 게시글 조회
    public List<Post> getAllPosts() throws Exception {
        List<Post> posts = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection("post").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            posts.add(document.toObject(Post.class));
        }
        return posts;
    }

    // 특정 번호의 게시글 조회
    public Post getPostById(String number) throws Exception {
        DocumentReference docRef = firestore.collection("post").document(number);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(Post.class);
        } else {
            return null;
        }
    }

    // 게시글 수정
    public void updatePost(String number, Post post) throws Exception {
        DocumentReference docRef = firestore.collection("post").document(number);

        // 번호를 제외한 필드만 업데이트
        docRef.update("제목", post.getTitle(), "글", post.getContent());
    }

    // 게시글 삭제
    public void deletePost(String number) throws Exception {
        firestore.collection("post").document(number).delete();
    }
}