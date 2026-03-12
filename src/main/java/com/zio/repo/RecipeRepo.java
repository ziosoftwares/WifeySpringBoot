package com.zio.repo;

import com.zio.data.dto.GeneralDTO;
import com.zio.data.entity.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNameLike(String name, Pageable pageable);
}
