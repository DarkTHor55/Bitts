package com.darkthor.Service;

import com.darkthor.Model.Comment;

import java.util.List;

public interface ICommentService {
    Comment addComment(Long postId, Long userId, String content);
    List<Comment> getCommentsByPostId(Long postId);
    boolean deleteComment(Long commentId, Long userId);
}
