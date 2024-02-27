package io.netstacker.latte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netstacker.latte.exception.ResourceNotFoundException;
import io.netstacker.latte.model.Post;
import io.netstacker.latte.repository.PostRepository;

@Service
public class PostService {
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(long postId)
    throws ResourceNotFoundException {
        return postRepository.findById(postId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Post not found for this id: " + postId)
            );
    }

    public void createPost(Post post) throws NullPointerException {
        if (post == null) throw new NullPointerException("Created post cannot be null");
        postRepository.save(post);
    }

    public Post updatePost(
        long postId,
        Post postDetails
    ) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId)
            .orElseThrow(() ->
                new ResourceNotFoundException("Post not found for this id: " + postId)
            );

        if (post == null) throw new NullPointerException("Post object cannot be null");

        if(postDetails.getName() != null) post.setName(postDetails.getName());
        if(postDetails.getText() != null) post.setText(postDetails.getText());
        if(postDetails.getAvatar() != null) post.setAvatar(postDetails.getAvatar());
        
        return postRepository.save(post);
    }

    public void deletePost(long postId) throws ResourceNotFoundException {
        Post postOptional = postRepository.findById(postId) .orElseThrow(() ->
                new ResourceNotFoundException("Post not found for this id: " + postId)
            );
        if (postOptional != null) {
            Post post = postOptional;
            postRepository.delete(post);
        }
    }
}
