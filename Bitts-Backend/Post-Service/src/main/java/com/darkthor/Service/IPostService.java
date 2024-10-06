package com.darkthor.Service;


import com.darkthor.Model.Post;
import com.darkthor.Request.PostRequest;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    public Post createPost(PostRequest post,Long userId);
    public List<Post> getAllPosts() ;
    public Optional<Post> getPostById(Long postId) ;
    public boolean deletePost(Long postId);
}

