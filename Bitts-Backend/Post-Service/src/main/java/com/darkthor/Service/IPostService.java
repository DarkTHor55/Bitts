package com.darkthor.Service;


import com.darkthor.Model.Post;
import com.darkthor.Request.PostRequest;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    public Post createPost(final PostRequest post,final Long userId);
    public List<Post> getAllPosts() ;
    public Optional<Post> getPostById(final Long postId) ;
    public boolean deletePost(final Long postId);
}

