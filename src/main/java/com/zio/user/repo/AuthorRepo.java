package com.zio.user.repo;

import com.zio.user.data.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepo extends JpaRepository<Author, Long> {
    @Query("select a.userName from Author a where a.userId = ?1")
    String findUserNameByUserId(Long userId);
}
