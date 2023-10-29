package com.ravindra.BloggingApp.Repo;

import com.ravindra.BloggingApp.Model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends CrudRepository<Comment,Integer> {
    Comment findByCommentBody(String commenBody);
}
