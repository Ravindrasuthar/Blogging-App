package com.ravindra.BloggingApp.Repo;

import com.ravindra.BloggingApp.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,Integer> {
    User findFirstByUserEmail(String email);
}
