package com.darkthor.Controller;

import com.darkthor.Jwt.JwtUtil;
import com.darkthor.Model.Post;
import com.darkthor.Service.ILikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/api/v1/user/post/like")
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final ILikeService likeService;
    private final JwtUtil jwtUtil;

    @GetMapping("/total")
    public int getTotalLikes(){
        return likeService.getTotalLikes();
    }
    @PostMapping("/addLike/{postId}")
    public ResponseEntity<?> addLike(@PathVariable("postId") Long postId, HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        Long userId;
        try {
            userId = jwtUtil.extractUserId(token);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid JWT Token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        likeService.addLike(postId, userId);
        return new ResponseEntity<>("Like added", HttpStatus.CREATED);
    }
    @DeleteMapping("/deleteLike/{postId}")
    public ResponseEntity<?> deleteLike(@PathVariable("postId") long postId ,HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        Long userId;
        try {
            userId = jwtUtil.extractUserId(token);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid JWT Token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        likeService.deleteLike(postId, userId);
        return new ResponseEntity<>("Like deleted", HttpStatus.OK);
    }



}
