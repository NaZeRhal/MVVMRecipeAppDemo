package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.mvvmrecipeappdemo.presentation.animation.PulsingCircle
import org.koin.android.viewmodel.ext.android.viewModel

class RecipeListFragment : Fragment() {

    private val recipeListViewModel: RecipeListViewModel by viewModel()

    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    PulsingCircle()
                }
//                Column {
//                    SearchAppBar(
//                        query = query,
//                        onQueryChanged = recipeListViewModel::onQueryChanged,
//                        onExecuteSearch = recipeListViewModel::newSearch,
//                        scrollToPosition = scrollToPosition,
//                        selectedCategory = selectedCategory,
//                        onSelectedCategoryChanged = recipeListViewModel::onSelectedCategoryChanged,
//                        keyboardController = keyboardController
//                    )
//                    Box(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        LazyColumn(
//                            state = LazyListState(
//                                firstVisibleItemIndex = 0
//                            )
//                        ) {
//
//                            itemsIndexed(items = recipes) { index, recipe ->
//                                RecipeCard(recipe = recipe, onClick = {})
//                            }
//                        }
//                        CircularProgressBar(isDisplayed = isLoading)
//                    }
//                }
            }
        }
    }
}