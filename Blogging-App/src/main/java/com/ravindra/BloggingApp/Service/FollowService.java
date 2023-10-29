package com.ravindra.BloggingApp.Service;

import com.ravindra.BloggingApp.Model.Follow;
import com.ravindra.BloggingApp.Model.User;
import com.ravindra.BloggingApp.Repo.FollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    FollowRepo followRepo;

    public boolean findByFollowerAndTarget(User follower, User target) {

        List<Follow> follows = followRepo.findByCurrentUserAndCurrentUserFollower(target,follower);
        return !follows.isEmpty();
    }

    public void startFollowing(User follower, User target) {
        Follow follow = new Follow(null,target,follower);
        followRepo.save(follow);
    }
}
