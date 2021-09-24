package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.mvvmrecipeappdemo.extensions.onSurfaceColor
import com.example.mvvmrecipeappdemo.extensions.paddingStartEnd
import com.example.mvvmrecipeappdemo.extensions.surfaceColor
import com.example.mvvmrecipeappdemo.presentation.components.FoodCategoryChip
import com.example.mvvmrecipeappdemo.presentation.components.RecipeCard
import org.koin.android.viewmodel.ext.android.viewModel

class RecipeListFragment : Fragment() {

    private val recipeListViewModel: RecipeListViewModel by viewModel()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("DBG", "onCreateView: ")
        return ComposeView(requireContext()).apply {
            setContent {
                val keyboardController = LocalSoftwareKeyboardController.current
                val recipes = recipeListViewModel.recipes.value
                val query = recipeListViewModel.query.value

                Column {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 8.dp,
                        color = Color.White
                    ) {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    value = query,
                                    onValueChange = { userInput ->
                                        recipeListViewModel.onQueryChanged(userInput)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(8.dp),
                                    label = {
                                        Text(text = "Search...")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Search
                                    ),
                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.Search,
                                            contentDescription = "search icon"
                                        )
                                    },
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            recipeListViewModel.newSearch(query)
                                            keyboardController?.hide()
                                        },
                                        onGo = {
                                            Log.i("DBG", "onCreateView: onGo=${query}")
                                        }
                                    ),
                                    textStyle = TextStyle(color = onSurfaceColor()),
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = surfaceColor()
                                    )
                                )
                            }
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = paddingStartEnd(8, 8)
                            ) {
                                for (category in getAllFoodCategories()) {
                                    item {
                                        FoodCategoryChip(
                                            category = category.value,
                                            onExecuteSearch = {
                                                Log.i("DBG", "onCreateView: change=${it}")
                                                recipeListViewModel.onQueryChanged(it)
                                                Log.i("DBG", "onCreateView: query=${it}")
                                                recipeListViewModel.newSearch(query)
                                            })
                                    }
                                }
                            }
                        }

                    }
                    LazyColumn {
                        itemsIndexed(items = recipes) { index, recipe ->
                            RecipeCard(recipe = recipe, onClick = {})
                        }
                    }
                }
            }
        }
    }
}