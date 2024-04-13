package io.netstacker.latte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.Blog;
import io.netstacker.latte.repository.BlogRepository;
import jakarta.validation.Valid;

@Service
public class BlogService {
    private BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(long blogId)
    throws ResourceNotFoundException {
        return blogRepository.findById(blogId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Blog not found for this id: " + blogId)
            );
    }

    public void createBlog(@Valid Blog blog) throws NullPointerException {
        if (blog == null) throw new NullPointerException("Created blog cannot be null");
        blogRepository.save(blog);
    }

    public Blog updateBlog(
        long blogId,
        @Valid Blog blogDetails
    ) throws ResourceNotFoundException {
        Blog blog = blogRepository.findById(blogId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Blog not found for this id: " + blogId)
            );

        if (blog == null) throw new NullPointerException("Blog object cannot be null");

        if(blogDetails.getName() != null) blog.setName(blogDetails.getName());
        if(blogDetails.getText() != null) blog.setText(blogDetails.getText());
        if(blogDetails.getAvatar() != null) blog.setAvatar(blogDetails.getAvatar());
        
        return blogRepository.save(blog);
    }

    public void deleteBlog(long blogId) throws ResourceNotFoundException {
        Blog blogOptional = blogRepository.findById(blogId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Blog not found for this id: " + blogId)
            );
        if (blogOptional != null) {
            Blog blog = blogOptional;
            blogRepository.delete(blog);
        }
    }
}
