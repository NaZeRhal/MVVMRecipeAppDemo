package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.repository.RecipeRepository
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.PAGE_SIZE
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.STATE_KEY_LIST_POSITION
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.STATE_KEY_PAGE
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.STATE_KEY_QUERY
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.STATE_KEY_SELECTED_CATEGORY
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.TOKEN_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipes = mutableStateOf<List<Recipe>>(listOf())
    val query = mutableStateOf("")
    val selectedCategory = mutableStateOf<FoodCategory?>(null)
    val isLoading = mutableStateOf(false)
    val page = mutableStateOf(1)
    val scrollToPosition = mutableStateOf(0)
    private var recipeListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { setPage(it) }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { setQuery(it) }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { setListScrollPosition(it) }
        savedStateHandle.get<FoodCategory?>(STATE_KEY_SELECTED_CATEGORY)
            ?.let { setSelectedCategory(it) }

        if (recipeListScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> newSearch()
                    is RecipeListEvent.NextPageEvent -> nextPage()
                    is RecipeListEvent.RestoreStateEvent -> restoreState()
                }
            } catch (e: Exception) {
                Log.i("DBG", "onTriggerEvent: ${e.message}")
            }
        }
    }

    private suspend fun restoreState() {
        isLoading.value = true
        val results = mutableListOf<Recipe>()
        for (p in 1..page.value) {
            val result = recipeRepository.search(
                token = TOKEN_KEY,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if (p == page.value) {
                recipes.value = results
                isLoading.value = false
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
        setListScrollPosition(position)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String) {
        setSelectedCategory(getFoodCategory(category))
        onQueryChanged(category)
    }

    //Append new recipes
    private fun appendRecipes(recipes: List<Recipe>) {
        var currentList = this.recipes.value
        currentList = currentList + recipes
        this.recipes.value = currentList
    }

    private fun incrementPage() {
        setPage(page.value + 1)
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
        setSelectedCategory(null)
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle[STATE_KEY_LIST_POSITION] = position
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle[STATE_KEY_PAGE] = page
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }

    private fun setSelectedCategory(selectedCategory: FoodCategory?) {
        this.selectedCategory.value = selectedCategory
        savedStateHandle[STATE_KEY_SELECTED_CATEGORY] = selectedCategory
    }
}