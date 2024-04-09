package com.example.github.web.DTO.comment;

import com.example.github.repository.comment.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentResponseDto {
    private Integer postId;
    private String name;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime time;


    public CommentResponseDto(Comment comment) {
        this.postId=comment.getPost().getPostId();
        this.name =comment.getName();
        this.content=comment.getContent();
        this.time=comment.getCreateAt();
    }
}
