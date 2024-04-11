package com.example.github.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostJpa extends JpaRepository<Post, Integer> {
    @Query(
            "SELECT p " +
                    "FROM Post p " +
                    "JOIN FETCH p.user u " +
                    "WHERE u.email = ?1 "
    )
    List findByEmailFetchJoin(String email);

    @Query(
            "SELECT p " +
                    "FROM Post p " +
                    "WHERE p.user.userId = ?1 "
    )
    List findByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(
            "DELETE " +
                    "FROM Post p " +
                    "WHERE p.user.userId = ?1 "
    )
    void deleteByUserId(Integer userId);
}
