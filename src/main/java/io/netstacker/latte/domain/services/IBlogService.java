package io.netstacker.latte.domain.services;

import java.util.List;

import io.netstacker.latte.application.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Blog;
import jakarta.validation.Valid;

public interface IBlogService {
    public List<Blog> getAllBlogs();

    public Blog getBlogById(long blogId) throws ResourceNotFoundException;

    public void createBlog(@Valid Blog blog) throws NullPointerException;

    public Blog updateBlog(long blogId, @Valid Blog blogDetails) throws ResourceNotFoundException;

    public void deleteBlog(long blogId) throws ResourceNotFoundException;
}
