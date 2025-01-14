package com.example.demo.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) throws Exception {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // 모든 게시글 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() throws Exception {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 특정 번호의 게시글 조회
    @GetMapping("/{number}")
    public ResponseEntity<Post> getPostByNumber(@PathVariable String number) throws Exception {
        Post post = postService.getPostById(number);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 수정 (번호 제외, 제목, 글 수정 가능)
    @PutMapping("/{number}")
    public ResponseEntity<Void> updatePost(@PathVariable String number, @RequestBody Post updatedPost) throws Exception {
        Post existingPost = postService.getPostById(number);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }

        // 번호는 변경할 수 없으므로, 제목과 글만 업데이트
        existingPost.set제목(updatedPost.get제목());
        existingPost.set글(updatedPost.get글());

        postService.updatePost(number, existingPost);
        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @DeleteMapping("/{number}")
    public ResponseEntity<Void> deletePost(@PathVariable String number) throws Exception {
        postService.deletePost(number);
        return ResponseEntity.noContent().build();
    }
}