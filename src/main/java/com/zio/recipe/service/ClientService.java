package com.zio.recipe.service;

import com.zio.common.data.api.Error;
import com.zio.recipe.data.ReceptionDTO;
import com.zio.recipe.data.RecipeCard;
import com.zio.recipe.data.RecipeDTO;
import com.zio.recipe.data.entity.*;
import com.zio.recipe.data.util.ObjectMapper;
import com.zio.recipe.repo.ReceptionRepo;
import com.zio.recipe.repo.RecipeMetasRepo;
import com.zio.recipe.repo.RecipeRepo;
import com.zio.user.repo.AuthorRepo;
import com.zio.common.util.SessionManager;
import com.zio.common.util.ZioException;
import com.zio.common.util.ZioRunTimeException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    RecipeRepo recipeRepo;
    @Autowired
    ReceptionRepo receptionRepo;
    @Autowired
    RecipeMetasRepo metasRepo;

    @Autowired
    AuthorRepo authorRepo;

    public List<RecipeCard> getFeatured(Map<String, Object> filter) {
        List<Long> filteredIds = metasRepo.findAll(buildSpec(filter)).stream().map(RecipeMetas::getRecipeId).toList();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("likesCount").descending());
        List<Long> ids = recipeRepo.findBestReceived(filteredIds, pageable);
        return recipeRepo.findAllById(ids).stream().map(recipe -> {
            try {
                return ObjectMapper.toRecipeCard(recipe, authorRepo.findById(recipe.getAuthorId()), metasRepo.findById(recipe.getId()), Optional.of(new ReceptionDTO(receptionRepo.findLikesCountByRecipeId(recipe.getId()), receptionRepo.isLikedBy(recipe.getId(), SessionManager.getUserId()))));
            } catch (ZioException e) {
                throw new ZioRunTimeException(new Error(515, 1, "NO_SESSION"));
            }
        }).toList();
    }

    public Specification<RecipeMetas> buildSpec(Map<String, Object> filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.containsKey("diet")) {
                predicates.add(cb.equal(root.get("diet"), Diet.valueOf((String) filters.get("diet"))));
            }

            if (filters.containsKey("difficulty")) {
                predicates.add(cb.equal(root.get("difficulty"), Difficulty.valueOf((String) filters.get("difficulty"))));
            }

            if (filters.containsKey("duration")) {
                predicates.add(cb.lessThanOrEqualTo(root.get("duration"), (Integer) filters.get("duration")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public void likeRecipe(Long recipeId) throws ZioException {
        var userId = SessionManager.getUserId();

        Reception reception = receptionRepo.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found"));

        Optional<Likes> existingLike = reception.getLikes()
                .stream()
                .filter(like -> like.getAuthorId().equals(userId))
                .findFirst();

        if (existingLike.isPresent()) {
            reception.getLikes().remove(existingLike.get());
            reception.setLikesCount(Math.max(0, reception.getLikesCount() - 1));
        }

        Likes like = new Likes();
        like.setAuthorId(userId);
        like.setRecipeId(reception);
        reception.getLikes().add(like);
        reception.setLikesCount(reception.getLikesCount() + 1);

    }
}
