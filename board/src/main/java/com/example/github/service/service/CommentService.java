package com.example.github.service.service;

import com.example.github.repository.comment.Comment;
import com.example.github.repository.comment.CommentJpa;
import com.example.github.repository.post.Post;
import com.example.github.repository.post.PostJpa;
import com.example.github.repository.user.User;
import com.example.github.repository.user.UserJpa;
import com.example.github.repository.userDetails.CustomUserDetails;
import com.example.github.service.exceptions.NotFoundException;
import com.example.github.web.DTO.ResponseDTO;
import com.example.github.web.DTO.comment.CommentDto;
import com.example.github.web.DTO.comment.CommentResponseDto;
import io.swagger.models.auth.In;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentJpa commentJpa;
    private final UserJpa userJpa;
    private final PostJpa postJpa;


    public List<CommentResponseDto> findAllComment(Integer postId) {
        Post post =postJpa.findById(postId)
                .orElseThrow(()->new NotFoundException("Post Id: "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        List<Comment> comments = commentJpa.findAllByPost(post);
        if (comments.isEmpty()){
            throw new NotFoundException("첫번째 댓글의 주인공이 되어보세요.");
        }
        List<CommentResponseDto> commentResponseDtos =comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    return commentResponseDtos;
    }


    @CacheEvict(value = "comments",allEntries = true)
    @Transactional
    public Boolean createResult(Integer postId,CustomUserDetails customUserDetails, CommentDto commentDto) {
        Integer userId =customUserDetails.getUserId();
        //토큰에 있는 userId로 user 찾기
        User user = userJpa.findById(userId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("User Id : "+userId+"에 해당하는 user가 존재하지 않습니다."));
        // PostId로 post를 찾은 다음 해당 post에 댓글 남기기
        Post post= postJpa.findById(postId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("Post Id: "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        LocalDateTime now = LocalDateTime.now();
        try {
            Comment comment = Comment.builder()
                    .post(post)
                    .user(user)
                    .content(commentDto.getContent())
                    .name(user.getName())
                    .createAt(now)
                    .build();
            commentJpa.save(comment);
            return true;
        }catch (Exception e){
            return false;
        }

    }
    @CacheEvict(value = "comments",allEntries = true)
    @Transactional
    public ResponseDTO deleteResults(Integer postId,List<Integer> commentIds, CustomUserDetails customUserDetails) {
        Integer userId =customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()-> new com.example.github.service.exceptions.NotFoundException("User Id : "+userId+"에 해당하는 user가 존재하지 않습니다."));
        Post post =postJpa.findById(postId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("Post Id : "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        List<Comment> commentsByPost = commentJpa.findAllByPost(post);
        if (commentsByPost.isEmpty()){
            throw new NotFoundException("해당 게시글에 댓글을 남기시지 않았습니다.");
        }
            List<Integer> postCommentId = commentsByPost.stream().map(Comment::getCommentId).toList();
            if (post.getUser().equals(user)){
                commentIds.retainAll(postCommentId);
                if (commentIds.isEmpty()){
                    throw new com.example.github.service.exceptions.NotFoundException("해당 게시판의 댓글이 아니거나 본인 댓글이 아니어서 삭제할 댓글이 없습니다.");
                }
                commentJpa.deleteAllById(commentIds);
                    return new ResponseDTO(HttpStatus.OK.value(),"Comments ("+ commentIds+") have been deleted."+ LocalDateTime.now());

            }else {
                List<Comment> comments = commentJpa.findAllByUser(user);
                List<Integer> commentIdList = comments.stream().map(Comment::getCommentId).collect(Collectors.toList());
                commentIds.retainAll(commentIdList);
                commentIds.retainAll(postCommentId);
                if (commentIds.isEmpty()){
                    throw new com.example.github.service.exceptions.NotFoundException("해당 게시판의 댓글이 아니거나 본인 댓글이 아니어서 삭제할 댓글이 없습니다.");
                }
                commentJpa.deleteAllById(commentIds);
                return new ResponseDTO(HttpStatus.OK.value(),"Comments ("+ commentIds+") have been deleted."+ LocalDateTime.now());
            }


    }
    public Boolean deleteResult(Integer postId, Integer commentId, CustomUserDetails customUserDetails) {
        Integer userId =customUserDetails.getUserId();
        User user = userJpa.findById(userId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("User Id : "+userId+"에 해당하는 user가 존재하지 않습니다."));
        Post post =postJpa.findById(postId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("Post Id : "+postId+"에 해당하는 게시판이 존재하지 않습니다."));
        List<Comment> comments = commentJpa.findAllByPost(post);
        if (comments.isEmpty()){
            throw new NotFoundException("해당 게시판에 삭제할 댓글이 없습니다.");
        }
        List<Comment> commentsByUser = commentJpa.findAllByUser(user);
        Comment comment = commentJpa.findById(commentId)
                .orElseThrow(()->new NotFoundException("Comment Id : "+commentId+"에 해당하는 comment가 존재하지 않습니다."));

        if (!comments.contains(comment)){
            throw new NotFoundException("해당 게시판 : "+ postId +"에 댓글이 아닙니다.");
        }
        try {
            if (post.getUser().equals(user) && comments.contains(comment)) {
                commentJpa.deleteById(commentId);
                return true;
            } else if (commentsByUser.contains(comment) && comments.contains(comment)) {
                commentJpa.deleteById(commentId);
                return true;
            } else return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @CacheEvict(value = "comments",allEntries = true)
    @Transactional
    public Boolean updateResult(Integer commentId,CustomUserDetails customUserDetails, CommentDto commentDto) {
        Integer userId =customUserDetails.getUserId();

        User user = userJpa.findById(userId)
                .orElseThrow(()->new com.example.github.service.exceptions.NotFoundException("User Id : "+userId+"에 해당하는 user가 존재하지 않습니다."));
        Comment updateComment =commentJpa.findById(commentId)
                .orElseThrow(()->new NotFoundException("Comment Id : "+commentId+"에 해당하는 comment가 존재하지 않습니다."));
        if (updateComment.getUser().equals(user)){
            updateComment.setContent(commentDto.getContent());
            return true;
        }else {
            throw new NotFoundException("해당  Comment Id : "+commentId+"은(는) User : "+user.getName()+"의 댓글이 아닙니다.");
        }
    }


    public List<CommentResponseDto> findAllCommentBykeyword(String keyword) {
        String lowKeyword = keyword.toLowerCase();
        List<Comment> comments = commentJpa.findAll();
        List<Comment> keywordComment = comments.stream().filter((comment)->comment.getContent().contains(lowKeyword)).collect(Collectors.toList());
        return keywordComment.stream().map(CommentResponseDto::new).collect(Collectors.toList());

    }

    public List<CommentResponseDto> findAllCommentByuUserId(CustomUserDetails customUserDetails) {
        Integer userId = customUserDetails.getUserId();
        User user=userJpa.findById(userId).orElseThrow(()->new NotFoundException("User Id : "+ userId+"에 해당하는 유저를 찾을 수 없습니다."));
        List<Comment> myComments = commentJpa.findAllByUser(user);
        return myComments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
