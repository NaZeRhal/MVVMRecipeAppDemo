package com.example.mvvmrecipeappdemo.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.example.mvvmrecipeappdemo.repository.RecipeRepository
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.STATE_KEY_RECIPE_ID
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.TOKEN_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipe = mutableStateOf<Recipe?>(null)
    val isLoading = mutableStateOf(false)

    init {
//        savedStateHandle.get<Int>(STATE_KEY_RECIPE_ID)?.let {
//            onTriggerEvent(GetRecipeEvent(it))
//        }
    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetRecipeEvent ->
                        if (recipe.value == null) {
                            Log.i("DBG", "onTriggerEvent: ${event.id}")
                            getRecipe(event.id)
                        }
                }
            } catch (e: Exception) {
                Log.i("DBG", "onTriggerEvent: ${e.message}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        isLoading.value = true
        delay(1000)

        val result = recipeRepository.qet(TOKEN_KEY, id)
        recipe.value = result

        savedStateHandle[STATE_KEY_RECIPE_ID] = result.id
        isLoading.value = false
    }
}