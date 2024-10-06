package com.darkthor.Repository;

import com.darkthor.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost_PostId(Long postId);
}

