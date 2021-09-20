package com.example.mvvmrecipeappdemo.network.model

import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(model: RecipeDto): Recipe = model.run {
        Recipe(
            id = id,
            title = title,
            publisher = publisher,
            featureImage = featureImage,
            rating = rating,
            sourceUrl = sourceUrl,
            description = description,
            cookingInstructions = cookingInstructions,
            ingredients = ingredients ?: listOf(),
            dateAdded = dateAdded,
            dateUpdated = dateUpdated
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto = domainModel.run {
        RecipeDto(
            id = id,
            title = title,
            publisher = publisher,
            featureImage = featureImage,
            rating = rating,
            sourceUrl = sourceUrl,
            description = description,
            cookingInstructions = cookingInstructions,
            ingredients = ingredients,
            dateAdded = dateAdded,
            dateUpdated = dateUpdated
        )
    }

    fun mapToDomainModelList(modelList: List<RecipeDto>): List<Recipe> =
        modelList.map { mapToDomainModel(it) }

    fun mapFromDomainModelList(domainModelList: List<Recipe>): List<RecipeDto> =
        domainModelList.map { mapFromDomainModel(it) }

}