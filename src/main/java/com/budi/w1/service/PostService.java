package com.budi.w1.service;

// import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budi.w1.repository.PostJpaRepository;
import com.budi.w1.repository.PostRepository;
import com.budi.w1.vo.Post;
import com.mysql.cj.util.StringUtils;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostJpaRepository postJpaRepository;

    // private static List<Post> posts;

    public Post getPost(Long id){
        // Post post = posts.get(id-1);
        Post post = postJpaRepository.findOneById(id);
        return post;
    }

    public List<Post> getPosts() {
        List<Post> postlist = postJpaRepository.findAllByOrderByUpdtDateDesc();    
        return postlist;
    }

    public List<Post> getPostsOrderByUpdtAsc(){
        List<Post> postlist = postJpaRepository.findAllByOrderByUpdtDateAsc();
        return postlist;
    }

    public List<Post> getPostsOrderByRegDesc(){
        List<Post> postlist = postRepository.findPostOrderByRegDateDesc();
        return postlist;
    }

    public List<Post> searchPostByTitle(String query){
        List<Post> posts = postJpaRepository.findByTitleContainingOrderByUpdtDateDesc(query);
        return posts;
    }

    public List<Post> searchPostByContent(String query){
        List<Post> posts = postJpaRepository.findByContentContainingOrderByUpdtDateDesc(query);
        return posts;
    }

    public boolean savePost(Post post){
        Post result = postJpaRepository.save(post);
        boolean isSuccess = true;

        if(result == null){
            isSuccess = false;
        }

        return isSuccess;

    }

    public boolean deletePost(Long id){
        Post result = postJpaRepository.findOneById(id);

        if(result == null){
            return false;
        }

        postJpaRepository.deleteById(id);

        return true;

    }

    public boolean updatePost(Post post){
        Post result = postJpaRepository.findOneById(post.getId());

        if(result == null){
            return false;
        }

        if(!StringUtils.isNullOrEmpty(post.getTitle())){
            result.setTitle(post.getTitle());
        }

        if(!StringUtils.isNullOrEmpty(post.getContent())){
            result.setContent(post.getContent());
        }

        postJpaRepository.save(result);

        return true;
    }
}
