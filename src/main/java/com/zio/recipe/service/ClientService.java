package com.zio.recipe.service;

import com.zio.data.api.Error;
import com.zio.recipe.data.ReceptionDTO;
import com.zio.recipe.data.RecipeDTO;
import com.zio.recipe.data.util.ObjectMapper;
import com.zio.recipe.repo.ReceptionRepo;
import com.zio.recipe.repo.RecipeRepo;
import com.zio.user.repo.AuthorRepo;
import com.zio.util.SessionManager;
import com.zio.util.ZioException;
import com.zio.util.ZioRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    RecipeRepo recipeRepo;
    @Autowired
    ReceptionRepo receptionRepo;

    @Autowired
    AuthorRepo authorRepo;

    public List<RecipeDTO> getFeatured() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("likesCount").descending());
        List<Long> ids = recipeRepo.findBestReceived(pageable);
        return recipeRepo.findAllById(ids).stream().map(
                recipe -> {
                    try {
                        return ObjectMapper.toRecipeDTO(
                                recipe,
                                authorRepo.findById(recipe.getAuthorId()),
                                Optional.of(new ReceptionDTO(receptionRepo.findLikesCountByRecipeId(recipe.getId()), receptionRepo.isLikedBy(recipe.getId(), SessionManager.getUserId())))
                        );
                    } catch (ZioException e) {
                        throw new ZioRunTimeException(new Error(515, "NO_SESSION", 1));
                    }
                }
        ).toList();
    }
}
