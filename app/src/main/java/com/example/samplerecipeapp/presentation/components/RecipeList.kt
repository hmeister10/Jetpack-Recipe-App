package com.example.samplerecipeapp.presentation.components

import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.samplerecipeapp.R
import com.example.samplerecipeapp.domain.model.Recipe
import com.example.samplerecipeapp.presentation.ui.recipe_list.PAGE_SIZE
import com.example.samplerecipeapp.presentation.ui.recipe_list.RecipeListEvent
import androidx.lifecycle.lifecycleScope



@ExperimentalAnimationApi
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    page: Int,
    onChangeRecipeListScrollPosition: (Int) -> Unit,
    onTriggerEvent: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        // #3 since order is reversed in Box
        LazyColumn {
            if(!loading && recipes.isNotEmpty()) {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeListScrollPosition(index)

                    if(index +1 >= page * PAGE_SIZE) {
                        onTriggerEvent(RecipeListEvent.NextPageEvent)
                    }

                    RecipeCard(recipe = recipe, onClick = {
                        if(recipe.id != null) {
                            val bundle = Bundle()
                            bundle.putInt("recipeId", recipe.id)
                            navController.navigate(R.id.viewRecipe, bundle)
                        }

                    })
                }
            } else {
                items(5) {
                    ShimmerRecipeCardItem(
                        colors = listOf(
                            Color.LightGray.copy(alpha = 0.9f),
                            Color.LightGray.copy(alpha = 0.2f),
                            Color.LightGray.copy(alpha = 0.9f)
                        ),
                        cardHeight = 250.dp
                    )
                }
            }

        }

        // #2 since order is reversed in Box
        CircularProgressBar(isDisplayed = loading)

        // #1 since order is reversed in Box
        DefaultSnackBar(
            snackbarHostState = scaffoldState.snackbarHostState,
            onDismiss = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}