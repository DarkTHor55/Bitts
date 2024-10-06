package com.darkthor.Service.Dto;


import com.darkthor.Model.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    public Post createPost(Post post);
    public List<Post> getAllPosts() ;
    public Optional<Post> getPostById(Long postId) ;
    public void deletePost(Long postId);
}

