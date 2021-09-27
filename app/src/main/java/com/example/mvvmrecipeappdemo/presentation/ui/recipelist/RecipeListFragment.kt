package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment
import com.example.mvvmrecipeappdemo.extensions.surfaceColor
import com.example.mvvmrecipeappdemo.presentation.components.*
import com.example.mvvmrecipeappdemo.presentation.theme.AppTheme
import com.example.mvvmrecipeappdemo.presentation.ui.MainApplication
import org.koin.android.viewmodel.ext.android.viewModel

class RecipeListFragment : Fragment() {

    private val recipeListViewModel: RecipeListViewModel by viewModel()
    private lateinit var application: MainApplication

    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        application = requireActivity().application as MainApplication

        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = recipeListViewModel.recipes.value
                val query = recipeListViewModel.query.value
                val selectedCategory = recipeListViewModel.selectedCategory.value
                val isLoading = recipeListViewModel.isLoading.value
                val keyboardController = LocalSoftwareKeyboardController.current

                val scrollToPosition by lazy {
                    val index = getCategoryIndex(selectedCategory)
                    if (index >= 1) index - 1 else 0
                }

                AppTheme(darkTheme = application.isDarkTheme.value) {
                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = recipeListViewModel::onQueryChanged,
                                onExecuteSearch = recipeListViewModel::newSearch,
                                scrollToPosition = scrollToPosition,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = recipeListViewModel::onSelectedCategoryChanged,
                                keyboardController = keyboardController,
                                onToggleTheme = { application.toggleTheme() }
                            )
                        },
                        bottomBar = { AppBottomBar() },
                        drawerContent = { AppNavDrawer() }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = surfaceColor())
                        ) {
                            LazyColumn(
                                state = LazyListState(firstVisibleItemIndex = 0)
                            ) {
                                itemsIndexed(items = recipes) { index, recipe ->
                                    RecipeCard(recipe = recipe, onClick = {})
                                }
                            }
                            CircularProgressBar(isDisplayed = isLoading)
                        }
                    }
                }
            }
        }
    }
}