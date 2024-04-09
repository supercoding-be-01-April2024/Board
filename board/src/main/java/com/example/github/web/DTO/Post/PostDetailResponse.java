package com.example.github.web.DTO.post;


import com.example.github.web.DTO.comment.CommentDto;
import com.example.github.web.DTO.comment.CommentResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponse {
    private Integer postId;
    private String title;
    private String name;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime createdAt;

    private List<CommentResponseDto> commentResponsDtoList;

    public PostDetailResponse(Integer postId, String title, String name, String content) {
        this.postId = postId;
        this.title = title;
        this.name = name;
        this.content = content;
    }
}
