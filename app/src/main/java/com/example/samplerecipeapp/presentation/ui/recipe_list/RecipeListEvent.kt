package com.example.samplerecipeapp.presentation.ui.recipe_list

sealed class RecipeListEvent {

    object NewSearchEvent: RecipeListEvent()
    object NextPageEvent: RecipeListEvent()

    // restore after processDeath
    object RestoreStateEvent: RecipeListEvent()
}
