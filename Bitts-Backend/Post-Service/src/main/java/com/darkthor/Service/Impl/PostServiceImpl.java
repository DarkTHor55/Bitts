package com.darkthor.Service.Dto.Impl;

import com.darkthor.Model.Post;
import com.darkthor.Repository.PostRepository;
import com.darkthor.Service.Dto.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {
    private final PostRepository postRepository;


    @Override
    public Post createPost(Post post) {


        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return List.of();
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return Optional.empty();
    }

    @Override
    public void deletePost(Long postId) {

    }
}
