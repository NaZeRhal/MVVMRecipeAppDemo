package com.example.mvvmrecipeappdemo.repository

import com.example.mvvmrecipeappdemo.domain.model.Recipe

interface RecipeRepository {

    suspend fun search(token: String, page: Int, query: String): List<Recipe>

    suspend fun qet(token: String, id: Int): Recipe
}