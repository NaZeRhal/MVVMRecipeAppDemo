package com.example.mvvmrecipeappdemo.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDto(
    @SerializedName("pk")
    val id: Int? = null,
    val title: String? = null,
    val publisher: String? = null,
    @SerializedName("feature_image")
    val featureImage: String? = null,
    val rating: Int? = 0,
    @SerializedName("source_url")
    val sourceUrl: String? = null,
    val description: String? = null,
    @SerializedName("cooking_instructions")
    val cookingInstructions: String? = null,
    val ingredients: List<String>? = null,
    @SerializedName("date_added")
    val dateAdded: String? = null,
    @SerializedName("date_updated")
    val dateUpdated: String? = null
) : Parcelable