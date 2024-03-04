package io.netstacker.latte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import io.netstacker.latte.model.User;
import io.netstacker.latte.service.PostService;
import io.netstacker.latte.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
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
    public ResponseEntity<Post> createPost(@CookieValue("token") String token, @RequestBody Post post) 
    throws ResourceNotFoundException {
        Long userId = TokenValidator.require(token);
        User user = userService.getUserById(userId);
        post.setUser(user);
        postService.createPost(post);
        return ResponseEntity.ok().body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(
        @CookieValue("token") String token,
        @PathVariable(value = "id") long postId,
        @RequestBody Post postDetails
    ) throws ResourceNotFoundException {
        TokenValidator.require(token);
        final Post updatedPost = postService.updatePost(postId, postDetails);
        return ResponseEntity.ok().body(updatedPost);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> deletePost(
        @CookieValue("token") String token,
        @PathVariable(value = "id") long postId
    ) throws ResourceNotFoundException {
        TokenValidator.require(token);
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}