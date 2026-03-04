package com.zio.wifey.data.recipe


enum class FoodType {
    VEGGIES,    // Vegetables
    MEAT,
    GROCERY, // Everything else
    FRUITS,
}


enum class Units(val textualRep: String) {
    GRAM("g"),
    LITERS("l"),
    CUPS("cps"),
    TABLESPOONS("sp"),
    TEASPOONS("sp"),
    PIECES("pcs"),
    NONE(""); // For ingredients where the unit is not applicable
}

