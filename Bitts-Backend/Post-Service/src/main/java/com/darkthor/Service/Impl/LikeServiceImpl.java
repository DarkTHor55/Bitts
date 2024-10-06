package com.darkthor.Service.Impl;

import com.darkthor.Model.Like;
import com.darkthor.Model.Post;
import com.darkthor.Repository.LikeRepository;
import com.darkthor.Repository.PostRepository;
import com.darkthor.Service.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements ILikeService {
    private  final LikeRepository likeRepository ;
    private final PostServiceImpl postService ;
    @Override
    public Like addLike(Long postId, Long userId) {
        Post post = postService.getPostById(postId).orElse(null);
        if (Objects.isNull(post)) {
            throw new IllegalArgumentException("Post not found");
        }
//        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
//            throw new IllegalArgumentException("User already liked this post");
//        }
        Like like = Like.builder()
                .userId(userId)
                .post(post)
                .build();
        Like savedLike = likeRepository.save(like);
        List<Like> likeList = post.getLikes();
        if (Objects.isNull(likeList)) {
            likeList = new ArrayList<>();
        }
        likeList.add(savedLike);
        post.setLikes(likeList);
        postService.updatePost(post.getPostId(), post);
        return savedLike;
    }


    @Override
    public int getTotalLikes() {
        List<Like> likes = likeRepository.findAll();
        return likes.size();
    }

    @Override
    public boolean deleteLike(Long id,long userId) {
        Post post = postService.getPostById(id).orElse(null);
        if (Objects.nonNull(post)){
            Like like = likeRepository.findById(id).orElse(null);
            if (Objects.nonNull(like) && like.getUserId().equals(userId)){
                likeRepository.deleteById(id);
                List<Like> likeList = post.getLikes();
                if(Objects.nonNull(likeList)) {
                    likeList.remove(like);
                }
                postService.updatePost(post.getPostId(),post);
                return true;
            }
        }
        return false;
    }
}
