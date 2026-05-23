package com.zio.ingred.service;

import com.zio.common.data.api.Error;
import com.zio.common.data.GeneralDTO;

import com.zio.ingred.data.entity.Ingred;
import com.zio.ingred.data.IngredDTO;
import com.zio.ingred.data.util.ObjectMapper;
import com.zio.ingred.repo.IngredRepo;
import com.zio.recipe.data.IngredQuantityDTO;
import com.zio.common.util.ZioException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredQueryService {


    @Autowired
    IngredRepo ingredRepo;


    private final ModelMapper mapper = new ModelMapper();

    public List<GeneralDTO> getIngredsLike(String name) {
        List<Ingred> result = ingredRepo.findByNameLike(name + "%", PageRequest.of(0, 10));
        if (result.isEmpty()) result = ingredRepo.findByNameLike("%" + name + "%", PageRequest.of(0, 10));

        return result.stream().map(ObjectMapper::toGenDTO).toList();
    }

    public List<IngredQuantityDTO> getIngredsOfMeals(List<Long> mealIds) {
        return new ArrayList<>();
    }

    @Cacheable(value = "ingred", key = "#id")
    public IngredDTO getIngred(Long id) throws ZioException {
        Ingred ingred = ingredRepo.findById(id).orElseThrow(() -> new ZioException(new Error(404, 4, "NO_SUCH_INGRED")));
        return ObjectMapper.toDTO(ingred);
    }

    public List<IngredDTO> getIngreds(List<Long> ids) {
        return ingredRepo.findAllById(ids).stream().map(ObjectMapper::toDTO).toList();
    }
}
