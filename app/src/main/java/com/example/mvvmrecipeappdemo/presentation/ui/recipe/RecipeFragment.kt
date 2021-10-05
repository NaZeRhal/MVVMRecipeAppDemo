package com.example.mvvmrecipeappdemo.presentation.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.mvvmrecipeappdemo.extensions.backgroundColor
import com.example.mvvmrecipeappdemo.presentation.components.CircularProgressBar
import com.example.mvvmrecipeappdemo.presentation.components.RecipeView
import com.example.mvvmrecipeappdemo.presentation.theme.AppTheme
import com.example.mvvmrecipeappdemo.presentation.ui.MainApplication
import com.example.mvvmrecipeappdemo.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.example.mvvmrecipeappdemo.utils.Constants
import org.koin.android.viewmodel.ext.android.viewModel

class RecipeFragment : Fragment() {

    private lateinit var application: MainApplication
    private val recipeId by lazy { arguments?.getInt(Constants.RECIPE_ID_KEY) }
    private val recipeViewModel: RecipeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DBG", "onCreate: Fragment")
        recipeId?.let { recipeViewModel.onTriggerEvent(GetRecipeEvent(it)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        application = requireActivity().application as MainApplication

        return ComposeView(requireContext()).apply {
            setContent {
                val isLoading = recipeViewModel.isLoading.value
                val recipe = recipeViewModel.recipe.value
                val scaffoldState = rememberScaffoldState()

                AppTheme(darkTheme = application.isDarkTheme.value) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = backgroundColor())
                        ) {
                            if (isLoading && recipe == null) {
                                CircularProgressBar(isDisplayed = isLoading)
                            } else {
                                recipe?.let {
                                    RecipeView(recipe = it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}