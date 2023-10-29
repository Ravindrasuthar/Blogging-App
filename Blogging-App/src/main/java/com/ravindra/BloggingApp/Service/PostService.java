package com.ravindra.BloggingApp.Service;

import com.ravindra.BloggingApp.Model.Post;
import com.ravindra.BloggingApp.Repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostRepo postRepo;

    public void savePost(Post post) {
        postRepo.save(post);
    }

    public Post getPostById(Integer postId) {
        return postRepo.findById(postId).orElseThrow();
    }

    public void removePost(Post blogPost) {
        postRepo.delete(blogPost);
    }
}
