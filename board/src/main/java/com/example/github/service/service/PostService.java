package com.example.github.service.service;

import com.example.github.repository.post.Post;
import com.example.github.repository.post.PostJpa;
import com.example.github.repository.user.User;
import com.example.github.repository.user.UserJpa;
import com.example.github.web.DTO.Post.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpa postJpa;
    private final UserJpa userJpa;

    public boolean createPost(PostRequest postRequest, String email) {
        User user = userJpa.findByEmailFetchJoin(email)
                .orElseThrow(()-> new NullPointerException("해당 이메일로 계정을 찾을 수 없습니다."));

        Post post = Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .name(user.getName())
                .content(postRequest.getContent())
                .likeCnt(0)
                .createdAt(LocalDateTime.now())
                .build();
        postJpa.save(post);
        return true;
    }

    public Integer getPostByEmail(String email) {
        return Long.valueOf(postJpa.findByEmailFetchJoin(email).stream().count()).intValue();
    }

    public Integer getAllPosts() {
        return postJpa.findAll().size();
    }

    public void modifyPost(PostRequest postRequest, Integer userId, Integer postId) {
        Integer postCnt = Long.valueOf(postJpa.findByUserId(userId).stream().count()).intValue();

        Optional<Post> existingPost = postJpa.findById(postId);

        if (postCnt > 0 && existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setCreatedAt(LocalDateTime.now());
            postJpa.save(post);
        } else {
            throw new NotFoundException("Failed to modify post.");
        }
    }



    public void deletePost(Integer userId) {
        Integer postCnt = Long.valueOf(postJpa.findByUserId(userId).stream().count()).intValue();

        if(postCnt < 0){
            throw new NotFoundException("Failed to delete post.");
        }

//        boolean userRole = authRole.contains("ROLE_USER");

//        if(userRole == true) {
//            postJpa.deleteAll();
//        }else {
            postJpa.deleteByUserId(userId);
//        }
    }

    public void likesPost(Integer userId, Integer postId) {
        Integer postCnt = Long.valueOf(postJpa.findByUserId(userId).stream().count()).intValue();

        Optional<Post> existingPost = postJpa.findById(postId);

        if (postCnt > 0 && existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setLikeCnt(post.getLikeCnt() + 1);
            postJpa.save(post);

        } else {
            throw new NotFoundException("게시글 좋아요 실패");
        }

    }
}
