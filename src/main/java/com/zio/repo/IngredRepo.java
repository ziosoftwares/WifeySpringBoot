package com.zio.repo;

import com.zio.data.dto.GeneralDTO;
import com.zio.data.entity.Ingred;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredRepo extends JpaRepository<Ingred, Long> {

    @Query("select new com.zio.data.dto.GeneralDTO(i.id, i.name, i.unit) from Ingred i where lower(i.name) like lower(?1)")
    List<GeneralDTO> findIngredLike(String name, Pageable pageable);

}
