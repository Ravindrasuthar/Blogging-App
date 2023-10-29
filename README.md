# Project Name 🏠
Blogging App

# Frameworks and Language Used
- **Spring Boot** 3.1.5
- **Java** 17
- **Maven** 3.8.1

## Data Flow

The Blogging application follows a structured data flow pattern to handle requests and manage data efficiently:

### Controller

The controller has endpoints for add a post, get a post by id, update a post, delete a post by id, add a comment, get a comment by id, update a comment by id, delete a comment by id and adding a follower corresponding to target user. 

The `@PostMapping` annotation is used for the `user/signUp`, `user/signIn`, `post`, `comment/post/{postId}` and `follow/user/{targetUserId}` endpoint to handle HTTP POST requests with a JSON request body containing a required object. In addition requestparam for email and tokenValue are also passed for authenticated access.
`@RequestParam` is used to extract individual parameter values from the request URL or submitted form data.

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
The `@GetMapping` annotation is used for the `post/by/postId` and `comment/by/{commentId}` endpoints to handle HTTP GET requests with a path variable for the postId and commentId.
The `@PathVariable` annotation is used to extract the required ID or any member variable from the request URL and pass it to the curresponding method.

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
The `@PutMapping` annotation is used for the `post`, `comment/by/{commentId}` endpoints to handle HTTP PUT requests with a JSON requestbody containing a post or a comment. In addition requestparam for email and tokenValue are also passed for authenticated access.
`@RequestParam` is used to extract individual parameter values from the request URL or submitted form data.

* _Post Owner can update a post :_
```java
 @PutMapping("post")
    public String updatePost(@RequestParam String email, @RequestParam String tokenValue,@RequestBody Post post)
    {
        return userService.updatePost(email,tokenValue,post);
    }
```

* _Commenter can update a comment :_
```java
 @PutMapping("comment/by/{commentId}")
    public String updateComment(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Integer commentId, @RequestBody String commenBody)
    {
        return userService.updateComment(email,tokenValue,commentId,commenBody);
    }
```

***
The `@DeleteMapping` annotation is used for the `user/signOut`, `post/by/{postId}` and `comment/by/{commentId}` endpoint to handle HTTP DELETE requests with a requestparam for email and token value for authentication and curresponding Id as pathvariable which need to be deleted.
`@RequestParam` is used to extract individual parameter values from the request URL or submitted form data.
The `@PathVariable` annotation is used to extract the required ID or any member variable from the request URL and pass it to the curresponding method.

* _User can sign out :_
```java
 @DeleteMapping("user/signOut")
    public String userSignOut(@RequestParam String email, @RequestParam String tokenValue)
    {
        return userService.userSignOut(email,tokenValue);
    }
```

* _Post owner can delete a post with its id :_
```java
@DeleteMapping("post/by/{postId}")
    public String deletePostById(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Integer postId)
    {
        return userService.deletePostById(email,tokenValue,postId);
    }
```

* _Post Owner or a commenter can delete a comment :_
```java
 @DeleteMapping("comment/by/{commentId}")
    public String deleteCommentById(@RequestParam String email, @RequestParam String tokenValue,@PathVariable Integer commentId)
    {
        return userService.deleteCommentById(email,tokenValue,commentId);
    }
```

The controller class also has an autowired all the required Service interface to handle business logic for the Blogging App.

This implementation demonstrates a basic setup for a REST API controller in Spring Boot, but it can be expanded upon and customized based on specific requirements for the Blogging App.

---

### Service

The Service layer encapsulates the core business logic and data processing. It interacts with the Repository layer to retrieve and store data. In this application, it handles operations like adding a post, retrieving a post, updating a post etc. The Service layer validates input data and performs necessary operations before returning results to the Controller.

* _User Service class contains all the objects as required through dependency injection using `@Autowired` and has all the methods to perform various business logic :_
```java
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

    // Service methods for various operations
    // ...
```
* _Like user service Blogging App has all the service class curresponding to their model._

---

### Repository

The repository layer is responsible for interacting with the database. It uses Spring Data JPA to perform CRUD (create, read, update, delete) operations on entities.

* _User Repository : It has a custom finder which will find a user curresponding to the mail passed._
```java
@Repository
public interface UserRepo extends CrudRepository<User,Integer> {
    User findFirstByUserEmail(String email);
}
```
* _Post Repository :_
```java
@Repository
public interface PostRepo extends CrudRepository<Post,Integer> {
}
```
* _AuthenticationToken Repository : It has a custom finder which will find a token curresponding to tokenValue passed._
```java
@Repository
public interface AuthenticationTokenRepo extends CrudRepository<AuthenticationToken,Integer> {
    AuthenticationToken findFirstByTokenValue(String tokenValue);
}
```
* _Comment Repository :_
```java
@Repository
public interface CommentRepo extends CrudRepository<Comment,Integer> {
}
```
* _Follow Repository : It has a custom finder which will find follow curresponding to target and follwer passed._
```java
@Repository
public interface FollowRepo extends CrudRepository<Follow,Integer> {
    List<Follow> findByCurrentUserAndCurrentUserFollower(User target, User follower);
}
```
---

In the application.properties all the text required for connection with MySQL database are written.
```java
spring.datasource.url=jdbc:mysql://65.0.17.185:3306/blogApp
spring.datasource.username=Ravindra
spring.datasource.password=12345
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true
```
---

I have used EmailHandler class to send the token to the user email when He/She will login using email and password.
```java
public class EmailHandler {

    private static final String EMAIL_USERNAME ="ravindrasuthar201@gmail.com";
    // ...
```
---

I have used PasswordEncryptor class to encrypt the password.
```java
public class PasswordEncryptor {

    public static String encrypt(String unHashedPassword) throws NoSuchAlgorithmException {
        MessageDigest md5=MessageDigest.getInstance("MD5");

        md5.update(unHashedPassword.getBytes());
        byte[]digested=md5.digest();
        return DatatypeConverter.printHexBinary(digested);

    }
}
```

---

## Database Used
I have used MySQL as DataBase.

## Project Structure
The project follows a standard Spring Boot application structure with components organized into layers:

- **Controller:** Handles incoming HTTP requests and defines API endpoints.
- **Service:** Implements business logic and interacts with the repository.
- **Repository:** Manages data access and storage.
- **Entity:** Defines data models.

## Project Summary
- The blogging app is a software application designed to facilitate the creation, publishing, and management of blog content.
- It provides a user-friendly interface for bloggers to write, edit, and format their posts without needing to understand complex web coding languages.
- In this project i have created different endpoints, custom finders, Validation, @OneToOne, @ManyToOne mapping between models.

## Acknowledgments
Thank you to the Spring Boot and Java communities for providing excellent tools and resources.

<!-- Contact -->
## Contact
For questions or feedback, please contact [Ravindra Suthar](mailto:ravindrasuthar201@gmail.com).


