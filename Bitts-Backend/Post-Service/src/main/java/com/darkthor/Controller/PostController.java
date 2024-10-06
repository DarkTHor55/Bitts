package com.darkthor.Controller;

import com.darkthor.Jwt.JwtUtil;
import com.darkthor.Request.PostRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api/v1/user/post")
@RequiredArgsConstructor
public class PostController {
    private final JwtUtil jwtUtil;
    @PostMapping
    public ResponseEntity<String>createPost(@RequestBody PostRequest Postrequest, HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        String email;
        try {
            email = jwtUtil.extractUsername(token);
            Long userId = jwtUtil.extractUserId(token);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid JWT Token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Post created", HttpStatus.CREATED);
    }


}
