package com.zio.wifey;

import com.zio.common.data.RecipeInfo;
import com.zio.common.util.ZioException;
import com.zio.recipe.service.RecipeQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FilteringTest {
    @Autowired
    RecipeQueryService recipeService;

    @Test
    public void testFilter() throws ZioException {
        List<RecipeInfo> recipeInfoList = recipeService.getRecipesByPreference();
        System.out.println("recipeInfoList = " + recipeInfoList);
    }
}
