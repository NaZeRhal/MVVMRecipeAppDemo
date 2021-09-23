package com.example.mvvmrecipeappdemo.presentation.ui.recipelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.mvvmrecipeappdemo.R
import com.example.mvvmrecipeappdemo.presentation.components.RecipeCard
import org.koin.android.viewmodel.ext.android.viewModel

class RecipeListFragment : Fragment() {

    private val recipeListViewModel: RecipeListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("DBG", "onCreateView: ")
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = recipeListViewModel.recipes.value

                LazyColumn {
                    itemsIndexed(items = recipes) { index, recipe ->
                        RecipeCard(recipe = recipe, onClick = {})
                    }
                }
            }
        }
    }
}