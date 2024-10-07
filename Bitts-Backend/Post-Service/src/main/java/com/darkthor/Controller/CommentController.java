package com.darkthor.Controller;

import com.darkthor.Jwt.JwtUtil;
import com.darkthor.Model.Comment;
import com.darkthor.Service.ICommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/post/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{postId}")
    public ResponseEntity<?> addComment(@PathVariable("postId") Long postId,@RequestBody String content,HttpServletRequest request) {
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

        Comment savedComment = commentService.addComment(postId, userId, content);
        if (savedComment != null) {
            return new ResponseEntity<>("Comment added successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        Long userId;
        try {
            userId = jwtUtil.extractUserId(token); // Extract userId from token (implement this method)
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid JWT Token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        boolean isDeleted = commentService.deleteComment(commentId, userId);
        if (isDeleted) {
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment not found or user not authorized", HttpStatus.NOT_FOUND);
        }
    }
}