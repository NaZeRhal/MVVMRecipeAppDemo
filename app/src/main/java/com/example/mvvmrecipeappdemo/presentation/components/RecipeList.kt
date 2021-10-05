package com.example.mvvmrecipeappdemo.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mvvmrecipeappdemo.R
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.extensions.surfaceColor
import com.example.mvvmrecipeappdemo.presentation.components.snackbars.DefaultSnackBar
import com.example.mvvmrecipeappdemo.presentation.ui.recipelist.RecipeListEvent
import com.example.mvvmrecipeappdemo.utils.Constants
import com.example.mvvmrecipeappdemo.utils.SnackBarController
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    isLoading: Boolean,
    scrollListToPosition: Int,
    page: Int,
    scaffoldState: ScaffoldState,
    snackBarController: SnackBarController,
    navController: NavController,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    onTriggerEvent: (RecipeListEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = surfaceColor())
    ) {
        LazyColumn(
            state = LazyListState(firstVisibleItemIndex = scrollListToPosition)
        ) {
            itemsIndexed(items = recipes) { index, recipe ->
                onChangeRecipeScrollPosition(index)
                if ((index + 1) >= (page * Constants.PAGE_SIZE) && !isLoading) {
                    onTriggerEvent(RecipeListEvent.NextPageEvent)
                }
                RecipeCard(
                    recipe = recipe,
                    onClick = {
                        if (recipe.id != null) {
                            val bundle = Bundle().apply {
                                putInt(Constants.RECIPE_ID_KEY, recipe.id)
                            }
                            navController.navigate(
                                R.id.action_recipeListFragment_to_recipeFragment,
                                bundle
                            )
                        } else {
                            snackBarController.getScope().launch {
                                snackBarController.showSnackBar(
                                    scaffoldState = scaffoldState,
                                    message = "Recipe error",
                                    actionLabel = "Ok"
                                )
                            }
                        }
                    })
            }
        }
        CircularProgressBar(isDisplayed = isLoading)
        DefaultSnackBar(
            snackBarHostState = scaffoldState.snackbarHostState,
            onDismiss = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}