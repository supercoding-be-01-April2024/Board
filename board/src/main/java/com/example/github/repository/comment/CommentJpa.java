package com.example.github.repository.comment;

import com.example.github.repository.post.Post;
import com.example.github.repository.user.User;
import com.example.github.service.exceptions.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentJpa extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByUser(User user);
    List<Comment> findAllByPost(Post post);

}

