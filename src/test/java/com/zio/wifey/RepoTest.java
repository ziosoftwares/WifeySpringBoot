package com.zio.wifey;

import com.zio.data.Allergen;
import com.zio.data.Cuisine;
import com.zio.repo.MealRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RepoTest {

    @Autowired
    MealRepo repo;

    @Test
    public void findbypref() {
        List<Long> expect = List.of(1L),
                ids = repo.findMealByPreference(List.of(), List.of(Cuisine.INDIAN), List.of(Allergen.DUMMY_ONLY_FOR_INTERNAL_USAGE));
        Assertions.assertEquals(expect.size(), ids.size());
        Assertions.assertTrue(ids.containsAll(expect) && expect.containsAll(ids));
    }
}
