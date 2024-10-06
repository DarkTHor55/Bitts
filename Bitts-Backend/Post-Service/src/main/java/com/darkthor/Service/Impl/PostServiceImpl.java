package com.darkthor.Service.Impl;

import com.darkthor.Model.Post;
import com.darkthor.Repository.PostRepository;
import com.darkthor.Request.PostRequest;
import com.darkthor.Service.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {
    private final PostRepository postRepository;


    @Override
    public Post createPost(final PostRequest post,final Long userId) {
        Post newPost = Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .userId(userId)
                .createdAt(new Date())
                .build();
        return postRepository.save(newPost);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(final Long postId) {
        return postRepository.findById(postId);
    }
    @Override
    public boolean deletePost(final Long postId) {
       Post p = postRepository.findById(postId).orElse(null);
       if(!Objects.isNull(p)){
           postRepository.deleteById(postId);
           return true;
       }
       else{
           log.error("Post with id: {} not found", postId);
           return false;
       }
    }
}
