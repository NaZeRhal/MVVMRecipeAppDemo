package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.repository.RecipeRepository
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.TOKEN_KEY
import kotlinx.coroutines.launch

class RecipeListViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    val query = mutableStateOf("")

    init {
        newSearch(query.value)
    }

    fun newSearch(query: String) {
        viewModelScope.launch {
            val result = recipeRepository.search(
                token = TOKEN_KEY,
                page = 1,
                query = query
            )
            recipes.value = result
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }
}