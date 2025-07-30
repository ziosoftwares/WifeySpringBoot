package com.zio.repo;

import com.zio.data.dto.IdNameDTO;
import com.zio.data.entity.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    @Query("select new com.zio.data.dto.IdNameDTO(i.id, i.name) from Recipe i where lower(i.name) like lower(?1)")
    List<IdNameDTO> findRecipeLike(String name, Pageable pageable);
}
