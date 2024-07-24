package io.netstacker.latte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.netstacker.latte.auth.TokenValidator;
import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.Blog;
import io.netstacker.latte.model.User;
import io.netstacker.latte.service.BlogService;
import io.netstacker.latte.service.UserService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    private final UserService userService;
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService, UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok().body(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(
        @PathVariable(value = "id") long blogId
    ) throws ResourceNotFoundException {
        Blog blog = blogService.getBlogById(blogId);
        return ResponseEntity.ok().body(blog);
    }

    @PostMapping("/")
    public ResponseEntity<Blog> createBlog(
        @CookieValue("token") String token,
        @RequestBody Blog blog
    ) throws ResourceNotFoundException {
        Long userId = TokenValidator.require(token);
        User user = userService.getUserById(userId);
        blog.setUser(user);
        blogService.createBlog(blog);
        return ResponseEntity.ok().body(blog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
        @CookieValue("token") String token,
        @PathVariable(value = "id") long blogId,
        @RequestBody Blog blogDetails
    ) throws ResourceNotFoundException {
        TokenValidator.require(token);
        final Blog updatedBlog = blogService.updateBlog(blogId, blogDetails);
        return ResponseEntity.ok().body(updatedBlog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBlog(
        @CookieValue("token") String token,
        @PathVariable(value = "id") long blogId
    ) throws ResourceNotFoundException {
        TokenValidator.require(token);
        blogService.deleteBlog(blogId);
        return ResponseEntity.ok().build();
    }
}