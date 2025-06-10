package com.zio.service;

import com.zio.data.dto.IdNameDTO;
import com.zio.repo.IngredRepo;
import com.zio.repo.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    RecipeRepo recipeRepo;

    @Autowired
    IngredRepo ingredRepo;


    public List<IdNameDTO> getIngredsLike(String name) {
        List<IdNameDTO> result = ingredRepo.findIngredLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = ingredRepo.findIngredLike("%" + name + "%", PageRequest.of(0, 10));
        return result;
    }

    public List<IdNameDTO> getRecipeLike(String name) {
        List<IdNameDTO> result = recipeRepo.findRecipeLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = recipeRepo.findRecipeLike("%" + name + "%", PageRequest.of(0, 10));
        return result;
    }
}
