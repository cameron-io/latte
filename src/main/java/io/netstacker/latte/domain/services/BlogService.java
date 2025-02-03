package io.netstacker.latte.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netstacker.latte.domain.exceptions.ResourceNotFoundException;
import io.netstacker.latte.domain.models.Blog;
import io.netstacker.latte.domain.repositories.BlogRepository;
import jakarta.validation.Valid;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(long blogId) throws ResourceNotFoundException {
        return blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found for this id: " + blogId));
    }

    public void createBlog(@Valid Blog blog) throws NullPointerException {
        if (blog == null)
            throw new NullPointerException("Created blog cannot be null");
        blogRepository.save(blog);
    }

    public Blog updateBlog(
            long blogId,
            @Valid Blog blogDetails) throws ResourceNotFoundException {
        var blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found for this id: " + blogId));

        if (blog == null)
            throw new NullPointerException("Blog object cannot be null");

        blog.setText(blogDetails.getText());
        blog.setName(blogDetails.getName());

        return blogRepository.save(blog);
    }

    public void deleteBlog(long blogId) throws ResourceNotFoundException {
        var blogOptional = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found for this id: " + blogId));
        if (blogOptional != null) {
            blogRepository.delete(blogOptional);
        }
    }
}
