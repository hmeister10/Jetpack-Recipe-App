package com.example.samplerecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplerecipeapp.domain.model.Recipe
import com.example.samplerecipeapp.presentation.ui.recipe_list.RecipeListEvent
import com.example.samplerecipeapp.repository.RecipeRepository
import com.example.samplerecipeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
    private val state: SavedStateHandle
): ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        // restore if process dies
        state.get<Int>(STATE_KEY_RECIPE)?.let { recipeId->
            onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when(event) {
                    is RecipeEvent.GetRecipeEvent -> {
                        getRecipeById(event.id)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "OnTriggerEvent: Exception $e, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipeById(recipeId: Int) {
        loading.value = true

        // simulate loading
        delay(1000)

        val recipe = repository.get(
            token = token,
            id = recipeId
        )
        this.recipe.value = recipe

        state.set(STATE_KEY_RECIPE, recipe.id)

        loading.value = false
    }


}