package io.netstacker.latte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.netstacker.latte.auth.TokenValidator;
import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.Post;
import io.netstacker.latte.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    @Value("${JWT_SECRET}")
    private String jwt_secret;

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable(value = "id") long postId)
    throws ResourceNotFoundException {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@CookieValue("token") String token, @RequestBody Post post) {
        Long userId = new TokenValidator(this.jwt_secret).require(token);
        post.setId(userId);
        postService.createPost(post);
        return ResponseEntity.ok().body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(
        @PathVariable(value = "id") long postId,
        @RequestBody Post postDetails
    ) throws ResourceNotFoundException {
        final Post updatedPost = postService.updatePost(postId, postDetails);
        return ResponseEntity.ok().body(updatedPost);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> deletePost(
        @PathVariable(value = "id") long postId
    ) throws ResourceNotFoundException {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}