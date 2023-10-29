package com.ravindra.BloggingApp.Repo;

import com.ravindra.BloggingApp.Model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends CrudRepository<Post,Integer> {
}
