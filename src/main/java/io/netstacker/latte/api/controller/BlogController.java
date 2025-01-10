package io.netstacker.latte.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.netstacker.latte.domain.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.services.BlogService;
import io.netstacker.latte.domain.services.AccountService;
import io.netstacker.latte.domain.models.Blog;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    private final AccountService accountService;
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService, AccountService accountService) {
        this.blogService = blogService;
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        var blogs = blogService.getAllBlogs();
        return ResponseEntity.ok().body(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(
        @PathVariable(value = "id") long blogId) throws ResourceNotFoundException {
        var blog = blogService.getBlogById(blogId);
        return ResponseEntity.ok().body(blog);
    }

    @PostMapping("/")
    public ResponseEntity<Blog> createBlog(
        @RequestAttribute("accountId") Long accountId,
        @RequestBody Blog blog) throws ResourceNotFoundException {
        var account = accountService.getAccountById(accountId);
        blog.setAccount(account);
        blogService.createBlog(blog);
        return ResponseEntity.ok().body(blog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
        @PathVariable(value = "id") long blogId,
        @RequestBody Blog blogDetails) throws ResourceNotFoundException {
        var updatedBlog = blogService.updateBlog(blogId, blogDetails);
        return ResponseEntity.ok().body(updatedBlog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBlog(
        @PathVariable(value = "id") long blogId) throws ResourceNotFoundException {
        blogService.deleteBlog(blogId);
        return ResponseEntity.ok().build();
    }
}