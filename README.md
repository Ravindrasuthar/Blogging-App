# Project Name 🏠
Blogging App

# Frameworks and Language Used
**Spring Boot** 3.1.5
**Java** 17
**Maven** 3.8.1

# Data Flow

_**Controller:**_ The controller has endpoints for add a post, get a post by id, update a post, delete a post by id, add a comment, get a comment by id, update a comment by id, delete a comment by id and adding a follower corresponding to target user.

The @PostMapping annotation is used for the user/signUp, user/signIn, user/signOut, post, comment/post/{postId} and follow/user/{targetUserId} endpoint to handle HTTP POST requests with a JSON request body containing a required object.

* _User can sign up, sign in and sign out :_
```java
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
```

* _User can post a blog post :_
```java
 @PostMapping("post")
    public String userPost(@RequestParam String email, @RequestParam String tokenValue, @RequestBody @Valid Post post)
    {
        return userService.userPost(email,tokenValue,post);
    }
```

* _User can comment on a blog post :_
```java
 @PostMapping("comment/post/{postId}")
    public String postComment(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Integer postId, @RequestBody String commentBody)
    {
        return userService.postComment(email,tokenValue,postId,commentBody);
    }
```

* _User can follow a target user :_
```java
@PostMapping("follow/user/{targetUserId}")
    public String followTarget(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Integer targetUserId)
    {
        return userService.followTarget(email,tokenValue,targetUserId);
    }
```

***
The @GetMapping annotation is used for the post/by/postId and comment/by/{commentId} endpoints to handle HTTP GET requests with a path variable for the postId and commentId. 
The @PathVariable annotation is used to extract the required ID or any member variable from the request URL and pass it to the curresponding method.

* _User can get a post by its id_
```java
 @GetMapping("post/by/{postId}")
    public Post getPostById(@PathVariable Integer postId)
    {
        return userService.getPostById(postId);
    }
```

* _User can get a comment by its id_
```java
 @GetMapping("comment/by/{commentId}")
    public Comment getCommentById(@PathVariable Integer commentId)
    {
        return userService.getCommentById(commentId);
    }
```

***

