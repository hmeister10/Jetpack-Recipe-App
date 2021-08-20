package com.example.samplerecipeapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.samplerecipeapp.R
import com.example.samplerecipeapp.domain.model.Recipe
import com.example.samplerecipeapp.presentation.ui.recipe_list.PAGE_SIZE
import com.example.samplerecipeapp.presentation.ui.recipe_list.RecipeListEvent

@Composable
fun RecipeDetail(
    recipe: Recipe?,
    loading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        if(!loading) {
            recipe?.featuredImage?.let {
                url -> Image(
                    painter = rememberImagePainter(
                        data = recipe?.featuredImage,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.empty_plate)
                        }
                    ),
                    contentDescription = " ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    contentScale = ContentScale.Crop
                )
            }

            recipe?.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp,
                            bottom = 12.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h6,
                    )
                }
            }

        } else {

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