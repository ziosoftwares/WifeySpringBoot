package com.zio.repo;

import com.zio.data.dto.GeneralDTO;
import com.zio.data.entity.Ingred;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredRepo extends JpaRepository<Ingred, Long> {

    List<Ingred> findByNameLike(String name, Pageable pageable);

}
