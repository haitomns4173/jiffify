from fastapi import FastAPI, Form
from fastapi.responses import JSONResponse
import os
import google.generativeai as genai

# Set up Google Generative AI API
os.environ['GOOGLE_API_KEY'] = "AIzaSyCPzguVayis6bSHsi83chcz6-8CbnLrHG0"
genai.configure(api_key=os.environ['GOOGLE_API_KEY'])

# Use the generative AI model
model = genai.GenerativeModel('gemini-pro')

# Initialize FastAPI
app = FastAPI()

# Predefined menu for the restaurant
MENU = [
    {"name": "Margherita Pizza", "type": "vegetarian"},
    {"name": "Mushroom trio Pizza", "type": "vegetarian"},
    {"name": "Jalapeno & Corn Pizza", "type": "vegetarian"},
    {"name": "Pepperoni Pizza", "type": "non-vegetarian"},
    {"name": "Hawaiian Pizza", "type": "vegetarian"},
    {"name": "Capricciosa Pizza", "type": "vegetarian"},
    {"name": "Roasted Chicken Pizza", "type": "non-vegetarian"},
    {"name": "Chicken Choila Pizza", "type": "non-vegetarian"},
    {"name": "Pesto Chicken Pizza", "type": "non-vegetarian"},
    {"name": "Meat Lover Pizza", "type": "non-vegetarian"},
    {"name": "Chilli Shrimp Pizza", "type": "non-vegetarian"},
    {"name": "Caprese Piadina", "type": "vegetarian"},
    {"name": "Fried Mushroom Piadina", "type": "vegetarian"},
    {"name": "Orange Glazzed Chicken Piadina", "type": "non-vegetarian"},
    {"name": "Gochujang Chicken Piadina", "type": "non-vegetarian"},
    {"name": "Chicken Tandoori Piadina", "type": "non-vegetarian"},
    {"name": "Balsamic Glazed Pulled Pork Piadina", "type": "non-vegetarian"},
    {"name": "Loaded Shrimp Piadina", "type": "non-vegetarian"},
    {"name": "Nostalgic Sweet Tooth Piadina", "type": "vegetarian"},
    {"name": "Creamy Pasta", "type": "vegetarian"},
    {"name": "Fettuccini Bolognese Pasta", "type": "non-vegetarian"},
    {"name": "Chicken & Pesto Fettuccini Pasta", "type": "non-vegetarian"},
    {"name": "Fettuccini Aglio e olio Pasta", "type": "vegetarian"},
    {"name": "Chilli Prawn Pasta", "type": "non-vegetarian"},
    {"name": "Bruschetta", "type": "vegetarian"},
    {"name": "Watermelon Salad", "type": "vegetarian"},
    {"name": "Nduza & Tomato Sugo", "type": "non-vegetarian"},
    {"name": "French Fries", "type": "vegetarian"},
    {"name": "Timmur Aloo", "type": "vegetarian"},
    {"name": "Chips Chilli", "type": "vegetarian"},
    {"name": "Peanut Sandheko", "type": "vegetarian"},
    {"name": "Spicy Corn", "type": "vegetarian"},
    {"name": "Mushroom Choila", "type": "vegetarian"},
    {"name": "Paneer Chilli", "type": "vegetarian"},
    {"name": "Spicy Crispy Paneer", "type": "vegetarian"},
    {"name": "Cheese Ball", "type": "vegetarian"},
    {"name": "Nachos", "type": "vegetarian"},
    {"name": "Chicken Sandheko", "type": "non-vegetarian"},
    {"name": "Keongs Chilli Chicken", "type": "non-vegetarian"},
    {"name": "Chicken Choila", "type": "non-vegetarian"},
    {"name": "Timmur Chicken", "type": "non-vegetarian"},
    {"name": "Timmur Pork", "type": "non-vegetarian"},
    {"name": "Spicy Pork", "type": "non-vegetarian"},
    {"name": "Pulled Pork Nachos", "type": "non-vegetarian"},
    {"name": "Piro Chicken", "type": "non-vegetarian"},
    {"name": "Honey Mustard", "type": "non-vegetarian"},
    {"name": "Dragon Chicken", "type": "non-vegetarian"},
    {"name": "Stuffed Chicken", "type": "non-vegetarian"},
    {"name": "Pork Chop", "type": "non-vegetarian"},
    {"name": "Veg Fried Rice", "type": "vegetarian"},
    {"name": "Egg Fried Rice", "type": "non-vegetarian"},
    {"name": "Chicken Fried Rice", "type": "non-vegetarian"},
    {"name": "Chicken Keema Noodles", "type": "non-vegetarian"},
    {"name": "Steam Veg Momo", "type": "vegetarian"},
    {"name": "Jhol Veg Momo", "type": "vegetarian"},
    {"name": "Jhaneko Veg Momo", "type": "vegetarian"},
    {"name": "Kothey Veg Momo", "type": "vegetarian"},
    {"name": "Steam Chicken Momo", "type": "non-vegetarian"},
    {"name": "Jhol Chicken Momo", "type": "non-vegetarian"},
    {"name": "Jhaneko Chicken Momo", "type": "non-vegetarian"},
    {"name": "Kothey Chicken Momo", "type": "non-vegetarian"},
    {"name": "Chicken Permigiani", "type": "non-vegetarian"},
    {"name": "Non Veg Sizzler", "type": "non-vegetarian"},
    {"name": "Veg Sizzler", "type": "vegetarian"},
    {"name": "Chocolate Fondant Desert", "type": "vegetarian"},
    {"name": "Cheese Cake Desert", "type": "vegetarian"},
    {"name": "Chicken & Quinoa", "type": "non-vegetarian"},
    {"name": "Rocket & Apple-Walnut", "type": "non-vegetarian"},    
]

@app.get("/")
async def root():
    return JSONResponse(content={"message": "Welcome to the API!"})
@app.post("/")
async def handle_input(
    mood: str = Form(...), 
    weather: str = Form(...), 
    hunger_level: str = Form(...), 
    food_type: str = Form(...)
):
    # Filter the menu based on food type
    filtered_menu = [item["name"] for item in MENU if item["type"] == food_type.lower()]
    
    if not filtered_menu:
        return JSONResponse(content={"response_data": "Sorry, no matching food items are available on the menu."}, status_code=200)

    # Prompt engineering for menu-based recommendation
    prompt = (
        f"The user is feeling {mood}. The weather is {weather}, their hunger level is {hunger_level}, "
        f"and they prefer {food_type} food. From the following menu: {', '.join(filtered_menu)}, "
        "suggest the most suitable food item. Provide just the food name."
    )
    
    # Generate response using the AI model
    try:
        response = model.generate_content(prompt)
        recommended_food = response.text.strip()
        # Ensure the recommendation is in the filtered menu
        if recommended_food not in filtered_menu:
            recommended_food = "Sorry, no suitable food recommendation could be made from the menu."
    except Exception as e:
        recommended_food = "Sorry, I couldn't generate a food recommendation at the moment. Please try again later."

    return JSONResponse(content={"response_data": recommended_food}, status_code=200)
