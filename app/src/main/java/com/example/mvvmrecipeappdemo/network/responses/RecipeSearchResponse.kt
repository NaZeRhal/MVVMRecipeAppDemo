package com.example.mvvmrecipeappdemo.network.responses

import com.example.mvvmrecipeappdemo.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    val count: Int,
    @SerializedName("results")
    val recipes: List<RecipeDto>
)