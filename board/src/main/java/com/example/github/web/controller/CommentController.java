package com.example.github.web.controller;

import com.example.github.repository.userDetails.CustomUserDetails;
import com.example.github.service.service.CommentService;
import com.example.github.web.DTO.ResponseDTO;
import com.example.github.web.DTO.comment.CommentDto;

import com.example.github.web.DTO.comment.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    //post에 해당하는 댓글 조회
    @GetMapping("/get/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO getComment(@PathVariable Integer postId){
        return new ResponseDTO(HttpStatus.OK.value(),"Comment retrieval success",commentService.findAllComment(postId));
    }
    //keyword에 해당하는 댓글 조회
    @GetMapping("/gets/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO getComment(@PathVariable String keyword){
        return new ResponseDTO(HttpStatus.OK.value(),"Comment retrieval success",commentService.findAllCommentBykeyword(keyword));
    }
    //내가 쓴 댓글만 조회
    @GetMapping("/get/mycomment")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO getComment(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return new ResponseDTO(HttpStatus.OK.value(),"Comment retrieval success",commentService.findAllCommentByuUserId(customUserDetails));
    }


    //💡날짜 형식 수정
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO createComment(@RequestParam("post-id") Integer postId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CommentDto commentDto){
        ResponseDTO createResult =commentService.createResult(postId,customUserDetails,commentDto);

        return createResult;
    }


    @DeleteMapping("/deletes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO deleteComments(@RequestParam("post-id") Integer postId,@RequestParam("comment-id") List<Integer> commentIds, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO deleteResult = commentService.deleteResults(postId,commentIds,customUserDetails);
       return deleteResult;

    }
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO deleteComment(@RequestParam("post-id") Integer postId,@RequestParam("comment-id") Integer commentId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO deleteResult = commentService.deleteResult(postId,commentId,customUserDetails);
        return deleteResult;

    }
    @PutMapping("/update/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO updateComment(@PathVariable Integer commentId, @AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody CommentDto commentDto){
        ResponseDTO updateResult = commentService.updateResult(commentId,customUserDetails,commentDto);

        return updateResult;

    }
}
