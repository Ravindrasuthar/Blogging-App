package com.ravindra.BloggingApp.Repo;

import com.ravindra.BloggingApp.Model.Follow;
import com.ravindra.BloggingApp.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepo extends CrudRepository<Follow,Integer> {
    List<Follow> findByCurrentUserAndCurrentUserFollower(User target, User follower);
}
