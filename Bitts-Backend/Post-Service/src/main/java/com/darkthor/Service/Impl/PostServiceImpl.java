package com.darkthor.Service.Impl;

import com.darkthor.Model.Comment;
import com.darkthor.Model.Like;
import com.darkthor.Model.MergeRequest;
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
    public Post updatePost(long postid,Post post) {
        Post p = postRepository.findById(postid).orElse(null);
        if(Objects.nonNull(p)){
            p.setTitle(post.getTitle());
            p.setContent(post.getContent());
            p.setUserId(post.getUserId());

            List<Comment>com = p.getComments();
            com.addAll(post.getComments());
            p.setComments(com);

            List<Like>likes = p.getLikes();
            likes.addAll(post.getLikes());
            p.setLikes(likes);

            List<MergeRequest>mg=p.getMergeRequests();
            mg.addAll(post.getMergeRequests());
            p.setMergeRequests(mg);
        }
        return postRepository.save(p);
    }
}
