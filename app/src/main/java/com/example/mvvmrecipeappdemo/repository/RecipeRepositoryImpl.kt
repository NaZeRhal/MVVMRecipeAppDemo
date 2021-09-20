package com.example.mvvmrecipeappdemo.repository

import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.network.RecipeService
import com.example.mvvmrecipeappdemo.network.model.RecipeDtoMapper

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> =
        mapper.mapToDomainModelList(recipeService.search(token, page, query).recipes)

    override suspend fun qet(token: String, id: Int): Recipe =
        mapper.mapToDomainModel(recipeService.get(token, id))

}