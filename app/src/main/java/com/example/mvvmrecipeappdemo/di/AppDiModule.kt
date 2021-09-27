package com.example.mvvmrecipeappdemo.di

import com.example.mvvmrecipeappdemo.network.RecipeService
import com.example.mvvmrecipeappdemo.network.model.RecipeDtoMapper
import com.example.mvvmrecipeappdemo.presentation.ui.recipe.RecipeViewModel
import com.example.mvvmrecipeappdemo.presentation.ui.recipelist.RecipeListViewModel
import com.example.mvvmrecipeappdemo.repository.RecipeRepository
import com.example.mvvmrecipeappdemo.repository.RecipeRepositoryImpl
import com.example.mvvmrecipeappdemo.utils.Constants
import com.example.mvvmrecipeappdemo.utils.SnackBarController
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataDiModule = module {
    single<RecipeService> { createRecipeService() }
    single<RecipeDtoMapper> { RecipeDtoMapper() }
    single<RecipeRepository> { RecipeRepositoryImpl(recipeService = get(), mapper = get()) }
}

val viewModelDiModule = module {
    viewModel<RecipeViewModel> { RecipeViewModel(recipeRepository = get()) }
    viewModel<RecipeListViewModel> { RecipeListViewModel(recipeRepository = get()) }
}

val uiDiModule = module {
    factory<SnackBarController> { (scope: CoroutineScope) -> SnackBarController(scope = scope) }
}

fun createRecipeService(): RecipeService {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(RecipeService::class.java)
}