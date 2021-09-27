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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mvvmrecipeappdemo.extensions.surfaceColor
import com.example.mvvmrecipeappdemo.presentation.components.CircularProgressBar
import com.example.mvvmrecipeappdemo.presentation.components.RecipeCard
import com.example.mvvmrecipeappdemo.presentation.components.SearchAppBar
import com.example.mvvmrecipeappdemo.presentation.components.snackbars.DefaultSnackBar
import com.example.mvvmrecipeappdemo.presentation.theme.AppTheme
import com.example.mvvmrecipeappdemo.presentation.ui.MainApplication
import com.example.mvvmrecipeappdemo.utils.Constants.Companion.PAGE_SIZE
import com.example.mvvmrecipeappdemo.utils.SnackBarController
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class RecipeListFragment : Fragment() {

    private lateinit var application: MainApplication
    private val recipeListViewModel: RecipeListViewModel by viewModel()

    //    private val snackBarController: SnackBarController by inject { parametersOf(lifecycleScope) }
    private val snackBarController = SnackBarController(lifecycleScope)

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
                                    recipeListViewModel.newSearch()
                                    if (recipes.isNotEmpty()) {
                                        snackBarController.getScope().launch {
                                            snackBarController.showSnackBar(
                                                scaffoldState = scaffoldState,
                                                message = "Found ${recipeListViewModel.selectedCategory.value} recipes",
                                                actionLabel = "Dismiss"
                                            )
                                        }
                                    }
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
                            LazyColumn(
                                state = LazyListState(firstVisibleItemIndex = scrollListToPosition)
                            ) {
                                itemsIndexed(items = recipes) { index, recipe ->
                                    recipeListViewModel.onChangeRecipeScrollPosition(index)
                                    if ((index + 1) >= (page * PAGE_SIZE) && !isLoading) {
                                        recipeListViewModel.nextPage()
                                    }
                                    RecipeCard(recipe = recipe, onClick = {})
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
                }
            }
        }
    }
}