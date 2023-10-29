package com.ravindra.BloggingApp.Controller;

import com.ravindra.BloggingApp.Model.Comment;
import com.ravindra.BloggingApp.Model.Post;
import com.ravindra.BloggingApp.Model.User;
import com.ravindra.BloggingApp.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("user/signUp")
    public String userSignUp(@Valid @RequestBody User user)
    {
        return userService.userSignUp(user);
    }

    @PostMapping("user/signIn")
    public String userSignIn(@RequestParam String email, @RequestParam String password)
    {
        return userService.userSignIn(email,password);
    }

    @DeleteMapping("user/signOut")
    public String userSignOut(@RequestParam String email, @RequestParam String tokenValue)
    {
        return userService.userSignOut(email,tokenValue);
    }

    @PostMapping("post")
    public String userPost(@RequestParam String email, @RequestParam String tokenValue, @RequestBody @Valid Post post)
    {
        return userService.userPost(email,tokenValue,post);
    }

    @GetMapping("post/by/{postId}")
    public Post getPostById(@PathVariable Integer postId)
    {
        return userService.getPostById(postId);
    }

    @PutMapping("post")
    public String updatePost(@RequestParam String email, @RequestParam String tokenValue,@RequestBody Post post)
    {
        return userService.updatePost(email,tokenValue,post);
    }

    @DeleteMapping("post/by/{postId}")
    public String deletePostById(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Integer postId)
    {
        return userService.deletePostById(email,tokenValue,postId);
    }


    @PostMapping("comment/post/{postId}")
    public String postComment(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Integer postId, @RequestBody String commentBody)
    {
        return userService.postComment(email,tokenValue,postId,commentBody);
    }

    @GetMapping("comment/by/{commentId}")
    public Comment getCommentById(@PathVariable Integer commentId)
    {
        return userService.getCommentById(commentId);
    }

    @PutMapping("comment/by/{commentId}")
    public String updateComment(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Integer commentId, @RequestBody String commenBody)
    {
        return userService.updateComment(email,tokenValue,commentId,commenBody);
    }

    @DeleteMapping("comment/by/{commentId}")
    public String deleteCommentById(@RequestParam String email, @RequestParam String tokenValue,@PathVariable Integer commentId)
    {
        return userService.deleteCommentById(email,tokenValue,commentId);
    }


    @PostMapping("follow/user/{targetUserId}")
    public String followTarget(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Integer targetUserId)
    {
        return userService.followTarget(email,tokenValue,targetUserId);
    }
}

