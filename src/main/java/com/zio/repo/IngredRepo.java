package com.zio.repo;

import com.zio.data.dto.IdNameDTO;
import com.zio.data.entity.meal.Ingred;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredRepo extends JpaRepository<Ingred, Long> {

    @Query("select new com.zio.data.dto.IdNameDTO(i.id, i.name) from Ingred i where lower(i.name) like lower(?1)")
    List<IdNameDTO> findIngredLike(String name, Pageable pageable);

}
