package com.zio.user.repo;

import com.zio.user.data.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorRepo extends JpaRepository<Author, Long> {
    @Query("select a.userName from Author a where a.userId = ?1")
    String findUserNameByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("update Author a set a.recipes = a.recipes+1 where a.userId = ?1")
    void increaseRecipe(Long userId);
}
