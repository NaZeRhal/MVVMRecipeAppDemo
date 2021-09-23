package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.TOKEN_KEY
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeListViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            val result = recipeRepository.search(
                token = TOKEN_KEY,
                page = 1,
                query = "chicken"
            )
            recipes.value = result
        }
    }
}