import json
import logging
import os

from fastapi import FastAPI, HTTPException
from groq import Groq

import local_secrets as secrets
from schemas import MealPlanResponse, PlanRequest

logging.basicConfig(level=logging.INFO)
log = logging.getLogger(__name__)

# ── prompt ────────────────────────────────────────────────────────────────────

SYSTEM_PROMPT = """You are a meal planner. Given a list of recipes, build a meal plan for the requested number of days.
Output ONLY valid JSON. No explanation. No markdown. No code blocks.
Output schema:
{
  "dayPlans": [
    {
      "day": 1,
      "breakfast": [recipeId...],
      "lunch": [recipeId...],
      "dinner": [recipeId...]
    }
  ]
}
Rules:
- Use only IDs from the provided recipe list
- Respect meal_type: BREAKFAST recipes go in breakfast, LUNCH in lunch, DINNER in dinner, ANY can go anywhere
- Each slot needs exactly one MAIN recipe; optionally 0-2 SIDE recipes
"""

# ── groq client ───────────────────────────────────────────────────────────────

# api_key = secrets.groq_key
api_key = os.getenv("GROQ_API_KEY")
client = Groq(api_key=api_key)

# ── core planner ──────────────────────────────────────────────────────────────

def call_llm(request: PlanRequest) -> str:
    user_message = (
        f"Available recipes:\n{request.model_dump_json()}\n\n"
        f"Build a {request.days}-day meal plan using only these recipes."
    )
    response = client.chat.completions.create(
        messages=[
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user",   "content": user_message},
        ],
        model="llama-3.3-70b-versatile",
        temperature=0.7,
    )
    return response.choices[0].message.content


def parse_response(raw: str) -> MealPlanResponse:
    try:
        return MealPlanResponse(**json.loads(raw))
    except json.JSONDecodeError:
        cleaned = raw.strip().removeprefix("```json").removeprefix("```").removesuffix("```").strip()
        return MealPlanResponse(**json.loads(cleaned))


def generate_plan(request: PlanRequest) -> MealPlanResponse:
    raw = call_llm(request)
    log.info("LLM raw output:\n%s", raw)
    return parse_response(raw)


# ── app ───────────────────────────────────────────────────────────────────────

app = FastAPI(title="Wifey — Meal Plan AI")


@app.post("/generate", response_model=MealPlanResponse)
def generate(request: PlanRequest) -> MealPlanResponse:
    try:
        return generate_plan(request)
    except json.JSONDecodeError as e:
        raise HTTPException(status_code=502, detail=f"LLM returned malformed JSON: {e}")
    except Exception as e:
        log.error("generation failed: %s", e)
        raise HTTPException(status_code=500, detail=str(e))


# ── dry run ───────────────────────────────────────────────────────────────────

if __name__ == "__main__":
    with open("sample.json") as f:
        request = PlanRequest(**json.load(f))
    result = generate_plan(request)
    print(result.model_dump_json(indent=2))