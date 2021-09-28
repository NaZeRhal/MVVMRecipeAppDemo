package com.example.mvvmrecipeappdemo.utils

abstract class Constants {

    companion object {
        const val BASE_URL = "https://food2fork.ca/api/recipe/"
        const val TOKEN_KEY = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
        const val PAGE_SIZE = 30

        const val STATE_KEY_PAGE = "recipe.state.page.key"
        const val STATE_KEY_QUERY = "recipe.state.query.key"
        const val STATE_KEY_LIST_POSITION = "recipe.state.list.position.key"
        const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.list.selected.category.key"
    }
}