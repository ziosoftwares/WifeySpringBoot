from enum import Enum
from typing import List
from pydantic import BaseModel


class Difficulty(str, Enum):
    EASY = "EASY"
    MEDIUM = "MEDIUM"
    HARD = "HARD"


class MealType(str, Enum):
    BREAKFAST = "BREAKFAST"
    LUNCH = "LUNCH"
    DINNER = "DINNER"
    ANY="ANY"


class Role(str, Enum):
    MAIN = "MAIN"
    SIDE = "SIDE"
    ANY = "ANY"


class Cuisine(str, Enum):
    SOUTH_INDIAN = "SOUTH_INDIAN"
    NORTH_INDIAN = "NORTH_INDIAN"
    ITALIAN = "ITALIAN"
    CHINESE = "CHINESE"


class Nutrition(BaseModel):
    calories: float
    protein: float
    carbs: float
    fat: float


class RecipeInfo(BaseModel):
    id: int
    name: str
    authorId: int
    duration: int
    difficulty: Difficulty
    mealType: MealType
    role: Role
    cuisine: Cuisine
    nutrition: Nutrition


class PlanRequest(BaseModel):
    days: int
    recipes: List[RecipeInfo]


class DayPlan(BaseModel):
    day: int
    breakfast: List[int]
    lunch: List[int]
    dinner: List[int]


class MealPlanResponse(BaseModel):
    dayPlans: List[DayPlan]