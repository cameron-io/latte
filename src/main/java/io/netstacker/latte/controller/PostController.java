package io.netstacker.latte.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.Post;
import io.netstacker.latte.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController {
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
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
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
    public Map<String, Boolean> deletePost(
        @PathVariable(value = "id") long postId
    ) throws ResourceNotFoundException {
        postService.deletePost(postId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}