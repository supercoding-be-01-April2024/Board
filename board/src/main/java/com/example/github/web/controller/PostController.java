package com.example.github.web.controller;

import com.example.github.repository.post.Post;
import com.example.github.repository.userDetails.CustomUserDetails;
import com.example.github.service.service.PostService;
import com.example.github.web.DTO.Post.PostRequest;
import com.example.github.web.DTO.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/post")
public class PostController {
    private final PostService postService;

    //게시글 생성
    @PostMapping("/create")
    public ResponseDTO register(@RequestBody PostRequest postRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        boolean isSuccess = postService.createPost(postRequest, customUserDetails.getEmail());

        if(isSuccess) return new ResponseDTO(HttpStatus.OK.value(), "post creation successful.");
        else return new ResponseDTO(400, "post creation fail");
    }

    //이메일로 게시글 조회
    @GetMapping("/find/{email}")
    public ResponseDTO getPostByEmail(@PathVariable String email) {
        Integer isSuccess = postService.getPostByEmail(email);

        if(isSuccess > 0) return new ResponseDTO(HttpStatus.OK.value(), String.valueOf("post find successful"));
        else return new ResponseDTO(400, "searching post by email fail");
    }

    //게시글 전체 조회
    @GetMapping("/findAll")
    public ResponseDTO getAllPosts() {
        Integer isSuccess = postService.getAllPosts();

        if(isSuccess > 0) return new ResponseDTO(HttpStatus.OK.value(), String.valueOf("Everypost find successful"));
        else return new ResponseDTO(400, "searching every post fail");
    }

    //게시글 수정
    @PostMapping("/modify/{postId}")
    public ResponseDTO modifyPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Integer postId) {
        try {
            postService.modifyPost(postRequest, customUserDetails.getUserId(), postId);
            return new ResponseDTO(HttpStatus.OK.value(), String.valueOf("Post modification successful."));
        } catch (NotFoundException e) {
            return new ResponseDTO(400, String.valueOf("Failed to modify post."));
        }
    }

    //게시글 삭제
    @GetMapping("/delete")
    public ResponseDTO deletePost(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            postService.deletePost(customUserDetails.getUserId());
            return new ResponseDTO(HttpStatus.OK.value(), String.valueOf("Deletion of post successful."));
        } catch (NotFoundException e) {
            return new ResponseDTO(400, String.valueOf("Failed to delete post."));
        }
    }

    //게시글 좋아요 누르기
    @GetMapping("/likes/{postId}")
    public ResponseDTO likesPost(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Integer postId) {
        try {
            postService.likesPost(customUserDetails.getUserId(), postId);
            return new ResponseDTO(HttpStatus.OK.value(), String.valueOf("게시글 좋아요 성공"));
        } catch (NotFoundException e) {
            return new ResponseDTO(400, String.valueOf("게시글 좋아요 실패"));
        }
    }
}
