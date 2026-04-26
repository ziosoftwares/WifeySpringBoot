package com.zio.ingred.repo;

import com.zio.ingred.data.entity.Ingred;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredRepo extends JpaRepository<Ingred, Long> {

    List<Ingred> findByNameLike(String name, Pageable pageable);

}
