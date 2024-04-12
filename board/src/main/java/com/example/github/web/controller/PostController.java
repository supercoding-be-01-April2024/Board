package com.example.github.web.controller;

import com.example.github.repository.userDetails.CustomUserDetails;
import com.example.github.service.service.PostService;
import com.example.github.web.DTO.post.PostRequest;
import com.example.github.web.DTO.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    //💡 ResponseDTO data
    @GetMapping("/find/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO getPostByEmail(@PathVariable String email,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return postService.getPostByEmail(email, customUserDetails.getEmail());
    }

    //게시글 전체 조회
    //💡 ResponseDTO data
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO getAllPosts() {
        return postService.getAllPosts();
    }

    //게시글 수정
    @PutMapping("/modify/{postId}")
    public ResponseDTO modifyPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Integer postId) {
        try {
            postService.modifyPost(postRequest, customUserDetails.getUserId(), postId);
            return new ResponseDTO(HttpStatus.OK.value(), "Post modification successful.");
        } catch (NotFoundException e) {
            return new ResponseDTO(400, "Failed to modify post.");
        }
    }

    //게시글 삭제
    //💡DeleteMapping으로 바꾸기
    @DeleteMapping("/delete")
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
    public ResponseDTO likesPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 @PathVariable Integer postId) {
        try {
            return postService.likesPost(customUserDetails.getUserId(), postId);
        } catch (NotFoundException e) {
            return new ResponseDTO(400, "게시글 좋아요 추가 실패");
        }
    }

    //게시글 상세 조회 (게시글 + 딸린 댓글까지 찾아오기)
    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/find/id/{postId}")
    public ResponseDTO findPostById(@PathVariable Integer postId){
        return postService.findPostById(postId);
    }
}
