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
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mvvmrecipeappdemo.extensions.surfaceColor
import com.example.mvvmrecipeappdemo.presentation.components.RecipeList
import com.example.mvvmrecipeappdemo.presentation.components.SearchAppBar
import com.example.mvvmrecipeappdemo.presentation.theme.AppTheme
import com.example.mvvmrecipeappdemo.presentation.ui.MainApplication
import com.example.mvvmrecipeappdemo.utils.SnackBarController
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RecipeListFragment : Fragment() {

    private lateinit var application: MainApplication
    private val recipeListViewModel: RecipeListViewModel by viewModel()
    private val snackBarController: SnackBarController by inject { parametersOf(lifecycleScope) }

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
                val page = recipeListViewModel.page.value
                val scrollListToPosition = recipeListViewModel.scrollToPosition.value

                val scrollToChipsPosition by lazy {
                    val index = getCategoryIndex(selectedCategory)
                    if (index >= 1) index - 1 else 0
                }

                val scaffoldState = rememberScaffoldState()

                AppTheme(darkTheme = application.isDarkTheme.value) {
                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = recipeListViewModel::onQueryChanged,
                                onExecuteSearch = {
                                    recipeListViewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                },
                                scrollToPosition = scrollToChipsPosition,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = recipeListViewModel::onSelectedCategoryChanged,
                                keyboardController = keyboardController,
                                onToggleTheme = { application.toggleTheme() }
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = surfaceColor())
                        ) {
                            RecipeList(
                                recipes = recipes,
                                isLoading = isLoading,
                                scrollListToPosition = scrollListToPosition,
                                page = page,
                                scaffoldState = scaffoldState,
                                snackBarController = snackBarController,
                                navController = findNavController(),
                                onChangeRecipeScrollPosition = recipeListViewModel::onChangeRecipeScrollPosition,
                                onTriggerEvent = recipeListViewModel::onTriggerEvent
                            )
                        }
                    }
                }
            }
        }
    }
}