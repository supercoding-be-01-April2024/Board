package com.example.github.repository.comment;

import com.example.github.repository.post.Post;
import com.example.github.repository.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="commentId")
@ToString
@Builder
@Entity
@Table(name = "comment")
public class Comment {
//	comment_id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT NOT NULL,
//    post_id INT NOT NULL,
//    name VARCHAR(100) NOT NULL,
//    content VARCHAR(500) NOT NULL,
//    create_at DATETIME,
//    FOREIGN KEY (user_id) REFERENCES user(user_id),
//    FOREIGN KEY (post_id) REFERENCES post(post_id)
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;
    @Column(name = "name",length = 100,nullable = false)
    private String name;
    @Column(name = "content",length = 500,nullable = false)
    private String content;
    @Column(name = "create_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime createAt;
}
