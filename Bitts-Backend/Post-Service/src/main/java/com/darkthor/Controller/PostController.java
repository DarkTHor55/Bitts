package com.darkthor.Controller;

import com.darkthor.Jwt.JwtUtil;
import com.darkthor.Model.Post;
import com.darkthor.Request.PostRequest;
import com.darkthor.Service.IPostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user/post")
@RequiredArgsConstructor
public class PostController {
    private final JwtUtil jwtUtil;
    private final IPostService postService;
    @PostMapping
    public ResponseEntity<String>createPost(final @RequestBody PostRequest postRequest,final HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        String email;
        Long userId;
        try {
            email = jwtUtil.extractUsername(token);
            userId = jwtUtil.extractUserId(token);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid JWT Token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        Post post=postService.createPost(postRequest,userId);
        if(!Objects.isNull(post))
            return new ResponseEntity<>("Post created", HttpStatus.CREATED);
        else{
            return new ResponseEntity<>("Failed to create post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId){
        return postService.getPostById(postId).map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        if(postService.deletePost(postId))
            return new ResponseEntity<>("Post deleted", HttpStatus.OK);
        else{
            return new ResponseEntity<>("Failed to delete post", HttpStatus.NOT_FOUND);
        }
    }


}
