package com.darkthor.Service.Impl;

import com.darkthor.Model.Comment;
import com.darkthor.Model.Post;
import com.darkthor.Repository.CommentRepository;
import com.darkthor.Repository.PostRepository;
import com.darkthor.Service.ICommentService;
import com.darkthor.Service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostServiceImpl postService;
    @Override
    public Comment addComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId).orElse(null);
        Comment comment = null;
        if (Objects.nonNull(post)) {
            comment = Comment.builder()
                    .userId(userId)
                    .post(post)
                    .content(content)
                    .build();
            commentRepository.save(comment);
        }

        List<Comment> comments = post.getComments();
        if (Objects.isNull(comments)) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        postService.updatePost(postId, post);
        return comment;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    @Override
    public boolean deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (Objects.nonNull(comment) && comment.getUserId().equals(userId)) {
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
}
