from groq import Groq
from scripts.groqs import local_secrets
import json

client = Groq(
    api_key=secrets.groq_key,
)
instruction = """You are a meal planner. Given a list of recipes with details, build a sensible meal plan for given number of days.
Output ONLY valid JSON. No explanation. No markdown. No code blocks.
output Schema:
{
  [
    "dayPlan" : {
      "day": 1,
      "breakfast": [recipeId, ...],
      "lunch": [recipeId, ...],
      "dinner": [recipeId, ...]
    }
  ]
}

Rules:
- Use only recipe IDs from the provided list
- Each slot must have at least one main recipe and optional sides not more than 2
"""

with open("sample.json", "r") as f:
    data = json.load(f)

user_message = f"""
Available recipes:
{data["recipes"]}
Build a {data["days"]}-day meal plan using only these recipes.
"""

chat_completion = client.chat.completions.create(
    messages=[
        {
            "role": "system",
            "content": instruction,
        },
        {
            "role": "user",
            "content": user_message,
        }
    ],
    model="llama-3.3-70b-versatile",
)

print(chat_completion.choices[0].message.content)
