package com.example.mvvmrecipeappdemo.presentation.ui.recipelist


enum class FoodCategory(val value: String) {
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    RICE("Rice"),
    VEGETARIAN("Vegetarian"),
    DESSERT("Dessert"),
    MILK("Milk"),
    CHEESE("Cheese"),
    PIZZA("Pizza"),
    DONUT("Donut"),
    SUSHI("Sushi"),
    FRUIT("Fruit")
}

fun getAllFoodCategories(): List<FoodCategory> = FoodCategory.values().toList()

fun getFoodCategory(value: String): FoodCategory? =
    getAllFoodCategories().firstOrNull { it.value == value }

fun getCategoryIndex(category: FoodCategory?) =
    category?.let { getAllFoodCategories().indexOf(it) } ?: 0
