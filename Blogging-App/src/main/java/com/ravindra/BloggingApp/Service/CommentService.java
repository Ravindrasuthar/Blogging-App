package com.ravindra.BloggingApp.Service;

import com.ravindra.BloggingApp.Model.Comment;
import com.ravindra.BloggingApp.Repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepo commentRepo;

    public void saveComment(Comment comment) {
        commentRepo.save(comment);
    }

    public Comment getCommentById(Integer commentId) {
        return commentRepo.findById(commentId).orElseThrow();
    }

    public void deleteCommentById(Integer commentId) {
        commentRepo.deleteById(commentId);
    }

    public Comment findByCommentBody(String commenBody) {
        return commentRepo.findByCommentBody(commenBody);
    }
}
