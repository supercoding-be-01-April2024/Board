package com.example.github.service.service;

import com.example.github.repository.comment.Comment;
import com.example.github.repository.comment.CommentJpa;
//import com.example.github.repository.likes.LikesJpa;
import com.example.github.repository.post.Post;
import com.example.github.repository.post.PostJpa;
import com.example.github.repository.user.User;
import com.example.github.repository.user.UserJpa;
import com.example.github.web.DTO.post.PostRequest;
import com.example.github.web.DTO.ResponseDTO;
import com.example.github.web.DTO.comment.CommentResponseDto;
import com.example.github.web.DTO.post.PostDetailResponse;
import com.example.github.web.DTO.post.PostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpa postJpa;
    private final UserJpa userJpa;
    private final CommentJpa commentJpa;
    //private final LikesJpa likesJpa;

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

        //likesJpa.deleteByUserId(userId);
        postJpa.deleteByUserId(userId);
    }

    public ResponseDTO likesPost(Integer userId, Integer postId) {
        Post post= postJpa.findById(postId)
                .orElseThrow(()-> new NotFoundException("아이디 "+ postId +"에 해당하는 게시글이 없습니다."));

        post.setLikeCnt(post.getLikeCnt() + 1);
        Post updatePost= postJpa.save(post);

        PostsResponse postsResponse = new PostsResponse(
                updatePost.getPostId(),
                updatePost.getTitle(),
                updatePost.getName(),
                updatePost.getLikeCnt(),
                updatePost.getCreatedAt());


        return new ResponseDTO(HttpStatus.OK.value(),"Like successfully added. Post number "+ postId + " has " +post.getLikeCnt() + " likes", postsResponse);
    }


    public ResponseDTO findPostById(Integer postId) {
        Post post= postJpa.findById(postId)
                .orElseThrow(()-> new NotFoundException("아이디 "+ postId +"에 해당하는 게시글이 없습니다."));
        List<Comment> comments= commentJpa.findAllByPost(post);
        List<CommentResponseDto> commentResponseDtoList= comments
                .stream()
                .map(c-> new CommentResponseDto(
                        c.getPost().getPostId(),
                        c.getName(),
                        c.getContent(),
                        c.getCreateAt()))
                .collect(Collectors.toList());

        PostDetailResponse postDetailResponse= PostDetailResponse
                .builder()
                .postId(postId)
                .title(post.getTitle())
                .name(post.getName())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .commentResponsDtoList(commentResponseDtoList)
                .build();

        return new ResponseDTO(HttpStatus.OK.value(), "게시글 상세 조회 성공", postDetailResponse);
    }
}
