package com.darkthor.Service;

import com.darkthor.Model.Like;

import java.util.List;

public interface ILikeService {
    Like addLike(Long postId, Long userId);
    int getTotalLikes();
    boolean deleteLike(Long id,long userId);
}
