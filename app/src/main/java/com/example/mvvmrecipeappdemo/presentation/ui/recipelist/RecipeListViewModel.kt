package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.repository.RecipeRepository
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.TOKEN_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipeListViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    val recipes = mutableStateOf<List<Recipe>>(listOf())
    val query = mutableStateOf("")
    val selectedCategory = mutableStateOf<FoodCategory?>(null)
    val isLoading = mutableStateOf(false)

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            isLoading.value = true
            resetSearchState()
            delay(500)
            val result = recipeRepository.search(
                token = TOKEN_KEY,
                page = 1,
                query = query.value
            )
            recipes.value = result
            isLoading.value = false
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        selectedCategory.value = getFoodCategory(category)
        onQueryChanged(category)
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }
}