package com.ravindra.BloggingApp.Service;

import com.ravindra.BloggingApp.Model.AuthenticationToken;
import com.ravindra.BloggingApp.Model.Comment;
import com.ravindra.BloggingApp.Model.Post;
import com.ravindra.BloggingApp.Model.User;
import com.ravindra.BloggingApp.Repo.UserRepo;
import com.ravindra.BloggingApp.Service.EmailUtility.EmailHandler;
import com.ravindra.BloggingApp.Service.HashingUtility.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationTokenService authenticationTokenService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;

    public String userSignUp(User user) {
        String email = user.getUserEmail();
        User existingUser = userRepo.findFirstByUserEmail(email);
        if(existingUser != null)
        {
            return email+ " is already registered please try sign in !!!";
        }
        String password = user.getUserPassword();

        try {
            String encryptedPass = PasswordEncryptor.encrypt(password);
            user.setUserPassword(encryptedPass);
            userRepo.save(user);
            return email+ " Registered Successfully !!!";

        } catch (NoSuchAlgorithmException e) {
            return "Internal Server issues while saving password, try again later !!!";
        }
    }

    public String userSignIn(String email, String password) {
        User existingUser = userRepo.findFirstByUserEmail(email);
        if(existingUser == null)
        {
            return "Not a valid email, please sign up first !!!";
        }
        try {
            String encryptedPass = PasswordEncryptor.encrypt(password);
            if(existingUser.getUserPassword().equals(encryptedPass))
            {
                AuthenticationToken token = new AuthenticationToken(existingUser);
                if(EmailHandler.sendEmail(email,"otp after login",token.getTokenValue()))
                {
                    authenticationTokenService.createToken(token);
                    return "sign in successful (please Check email for otp/token) !!!";
                }
                else
                {
                    return "Error while generating token !!!";
                }
            }
            else
            {
                return "Invalid Credentials !!!";
            }
        } catch (NoSuchAlgorithmException e) {
            return "Internal Server issues while saving password, try again later !!!";
        }
    }

    public String userSignOut(String email, String tokenValue) {

        if(authenticationTokenService.authenticate(email,tokenValue)) {
            authenticationTokenService.deleteToken(tokenValue);
            return "Sign Out successful!!";
        }
        else {
            return "Un Authenticated access!!!";
        }
    }

    public String userPost(String email, String tokenValue, Post post) {

        if(authenticationTokenService.authenticate(email,tokenValue))
        {
            User postOwner = userRepo.findFirstByUserEmail(email);
            post.setPostOwner(postOwner);

            postService.savePost(post);
            return postOwner.getUserHandle()+ " posted a post !!!";
        }
        else
        {
            return "Un Authenticated Access !!!";
        }
    }

    public String deletePostById(String email, String tokenValue, Integer postId) {

        if(authenticationTokenService.authenticate(email,tokenValue))
        {
            Post blogPost = postService.getPostById(postId);
            String postOwnerEmail = blogPost.getPostOwner().getUserEmail();
            if(email.equals(postOwnerEmail))
            {
                postService.removePost(blogPost);
                return (userRepo.findFirstByUserEmail(email)).getUserHandle()+" deleted post with post id: "+postId;
            }
            else
            {
                return "Un authorized access !!!";
            }
        }
        else
        {
            return "Un Authenticated access !!!";
        }
    }

    public Post getPostById(Integer postId) {
        Post post = postService.getPostById(postId);
        if(post == null)
        {
            return null;
        }
        else
        {
            return post;
        }
    }

    public String updatePost(String email, String tokenValue, Post post) {
        if(authenticationTokenService.authenticate(email,tokenValue))
        {
            Post blogPost = postService.getPostById(post.getPostId());
            if(blogPost == null)
            {
                return "Post does not exist !!!";
            }
            if(blogPost.getPostOwner().getUserEmail().equals(email))
            {
                blogPost.setPostContent(post.getPostContent());
                blogPost.setPostTitle(post.getPostTitle());
                blogPost.setPostAddress(post.getPostAddress());
                blogPost.setPostLocation(post.getPostLocation());
                blogPost.setPostType(post.getPostType());
                blogPost.setPostCreationDateTime(post.getPostCreationDateTime());
                blogPost.setPostOwner(userRepo.findFirstByUserEmail(email));
                postService.savePost(blogPost);
                return blogPost.getPostOwner().getUserHandle()+" Updated a post with post id : "+blogPost.getPostId();
            }
            else
            {
                return "Post does not belong to "+ (userRepo.findFirstByUserEmail(email)).getUserName();
            }
        }
        else
        {
            return "Un Authenticated Access !!!";
        }

    }

    public String postComment(String email, String tokenValue, Integer postId, String commentBody) {
        if(authenticationTokenService.authenticate(email,tokenValue))
        {
            Post postToBeCommented = postService.getPostById(postId);
            User commentor  = userRepo.findFirstByUserEmail(email);
            Comment comment = new Comment(null,commentBody, LocalDateTime.now(),commentor,postToBeCommented);
            commentService.saveComment(comment);
            return commentor.getUserHandle()+" made a comment on post with id : "+ postId;
        }
        else
        {
            return "Un Authenticated Access !!!";
        }
    }

    public Comment getCommentById(Integer commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if(comment != null)
        {
            return comment;
        }
        else
        {
            return null;
        }
    }

    public String deleteCommentById(String email, String tokenValue, Integer commentId) {
        if(authenticationTokenService.authenticate(email,tokenValue))
        {
            Comment comment = commentService.getCommentById(commentId);
            Post blogPostOfComment = comment.getBlogPost();
            if(authorizeCommentRemover(email,blogPostOfComment,comment))
            {
                commentService.deleteCommentById(commentId);
                return "Comment deleted !!!";
            }
            else
            {
                return "Not Authorized !!!";
            }
        }
        else
        {
            return "Un Authenticated Access !!!";
        }
    }

    private boolean authorizeCommentRemover(String email, Post blogPostOfComment, Comment comment) {
        User potentialUser = userRepo.findFirstByUserEmail(email);
        return potentialUser.equals(blogPostOfComment.getPostOwner()) || potentialUser.equals(comment.getCommenter());
    }


    public String updateComment(String email, String tokenValue, Integer commentId, String commenBody) {

        if(authenticationTokenService.authenticate(email,tokenValue))
        {
            Comment existingComment = commentService.getCommentById(commentId);
            if(existingComment == null)
            {
                return "Comment not found !!!";
            }
            User commenter = userRepo.findFirstByUserEmail(email);
            Post commenterPost = existingComment.getBlogPost();
            if(existingComment.getCommenter().equals(commenter))
            {
                existingComment.setCommenter(commenter);
                existingComment.setCommentBody(commenBody);
                existingComment.setBlogPost(commenterPost);
                existingComment.setCommentCreationDateTime(LocalDateTime.now());
                commentService.saveComment(existingComment);
                return commenter.getUserHandle()+ " updated a comment with comment id : "+commentId;
            }
            else
            {
                return "Not Authorized !!!";
            }

        }
        else
        {
            return "Un Authenticated Access !!!";
        }
    }

    public String followTarget(String email, String tokenValue, Integer targetUserId) {

        if(authenticationTokenService.authenticate(email,tokenValue))
        {
            User follower = userRepo.findFirstByUserEmail(email);
            User target = userRepo.findById(targetUserId).orElseThrow();

            if(authorizeToFollow(follower,target))
            {
                followService.startFollowing(follower,target);
                return follower.getUserHandle()+" starts following "+target.getUserHandle();
            }
            else
            {
                return follower.getUserHandle()+" already follows "+target.getUserHandle()+ " ,cannot re-follow";
            }
        }
        else
        {
            return "Un Authenticated Access !!!";
        }
    }

    private boolean authorizeToFollow(User follower, User target) {

        boolean followerExist = followService.findByFollowerAndTarget(follower,target);
        return !followerExist && !follower.equals(target);
    }


}
