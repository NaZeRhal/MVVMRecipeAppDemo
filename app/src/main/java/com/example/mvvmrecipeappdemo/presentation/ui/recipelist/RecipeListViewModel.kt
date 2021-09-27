package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.repository.RecipeRepository
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.PAGE_SIZE
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.TOKEN_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipeListViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    val recipes = mutableStateOf<List<Recipe>>(listOf())
    val query = mutableStateOf("")
    val selectedCategory = mutableStateOf<FoodCategory?>(null)
    val isLoading = mutableStateOf(false)
    val page = mutableStateOf(1)
    val scrollToPosition = mutableStateOf(0)
    private var recipeListScrollPosition = 0

    init {
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> newSearch()
                    is RecipeListEvent.NextPageEvent -> nextPage()
                }
            } catch (e: Exception) {
                Log.i("DBG", "onTriggerEvent: ${e.message}")
            }
        }
    }

    private suspend fun newSearch() {
        isLoading.value = true
        resetSearchState()
        delay(1000)
        val result = recipeRepository.search(
            token = TOKEN_KEY,
            page = page.value,
            query = query.value
        )
        recipes.value = result
        isLoading.value = false
    }

    private suspend fun nextPage() {
        //prevent duplicate events
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            scrollToPosition.value = recipeListScrollPosition - 1
            isLoading.value = true
            incrementPage()
            delay(1000)
            if (page.value > 1) {
                val result = recipeRepository.search(
                    token = TOKEN_KEY,
                    page = page.value,
                    query = query.value
                )
                appendRecipes(result)
            }
            isLoading.value = false
        }
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        selectedCategory.value = getFoodCategory(category)
        onQueryChanged(category)
    }

    //Append new recipes
    private fun appendRecipes(recipes: List<Recipe>) {
        var currentList = this.recipes.value
        currentList = currentList + recipes
        this.recipes.value = currentList
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        scrollToPosition.value = 0
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }
}