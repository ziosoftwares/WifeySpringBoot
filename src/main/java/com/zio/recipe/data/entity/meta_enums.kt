package com.zio.recipe.data.entity

enum class Cuisine {
    SOUTH_INDIAN,
    NORTH_INDIAN,
    ITALIAN,
    CHINESE,
    FRENCH,
    AMERICAN,
}

enum class Diet {
    VEGAN,
    VEGETARIAN,
    EGGETARIAN,
    PESCATARIAN,
    NONVEGETARIAN
}

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}

enum class MealType {
    ANY,
    BREAKFAST,
    LUNCH,
    DINNER
}

enum class Role {
    ANY,
    MAIN,
    SIDE

}

enum class Allergen {
    NONE,
    DAIRY,
    GLUTEN,
    SEAFOOD,
    NUTS,
    SOY,
}