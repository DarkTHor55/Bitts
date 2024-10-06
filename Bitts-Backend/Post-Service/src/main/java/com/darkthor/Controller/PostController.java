package com.darkthor.Controller;

import com.darkthor.Jwt.JwtUtil;
import com.darkthor.Model.Post;
import com.darkthor.Request.PostRequest;
import com.darkthor.Service.IPostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user/post")
@RequiredArgsConstructor
public class PostController {
    private final JwtUtil jwtUtil;
    private final IPostService postService;
    @PostMapping
    public ResponseEntity<String>createPost(@RequestBody PostRequest postRequest, HttpServletRequest request){
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


}
