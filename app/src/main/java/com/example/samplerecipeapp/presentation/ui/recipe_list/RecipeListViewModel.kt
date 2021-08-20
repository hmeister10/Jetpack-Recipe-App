package com.example.samplerecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplerecipeapp.domain.model.Recipe
import com.example.samplerecipeapp.repository.RecipeRepository
import com.example.samplerecipeapp.util.TAG
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    val query: MutableState<String> = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Int = 0

    var loading = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    init {

        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let {p -> setPage(p)}
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q -> setQuery(q)}
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c -> setSelectedCategory(c)}
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p -> setListScrollPosition(p)}

        if(recipeListScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }

    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when(event) {
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "OnTriggerEvent: Exception $e, ${e.cause}")
            }
        }
    }

    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        delay(1000)
        val result = repository.search(token = token, page = 1, query = query.value)
        recipes.value = result
        loading.value = false
    }

    fun onQueryChanged(query: String) {
        setQuery(query = query)
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    fun onChangeCategoryChangedPosition(position:Int) {
        setListScrollPosition(position= position)
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeListScrollPosition(0)
        if(selectedCategory.value?.value !== query.value) {
            clearSelectedCategory()
        }
    }

    private suspend fun nextPage() {
        // prevent duplicate events due to recompose happening too quickly
        if( ( recipeListScrollPosition + 1 ) >= ( page.value * PAGE_SIZE ) ) {
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered ${page.value}")

            delay(1000)

            if(page.value > 1 ) {
                val result = repository.search(
                    token,
                    page = page.value,
                    query = query.value
                )
                Log.d(TAG, "nextPage: $result")
                appendRecipes(result)

            }
            loading.value = false
        }
    }

    // Append new recipes to the existing list of recipies
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeRecipeListScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for(p in 1..page.value) {
            val result = repository.search(
                token = token,
                page = p,
                query = query.value,
            )
            results.addAll(result)
            if(p == page.value) {
                recipes.value = results
                loading.value = false
            }
        }
    }

}