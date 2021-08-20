package com.example.samplerecipeapp.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.samplerecipeapp.R
import com.example.samplerecipeapp.domain.model.Recipe

@ExperimentalAnimationApi
@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        val buttonState = remember { mutableStateOf(false) }

        Column() {
            recipe.featuredImage?.let {
                    url -> Image(
                painter = rememberImagePainter(
                    data = recipe.featuredImage,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.empty_plate)
                    }
                ),
                contentDescription = " ",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp),
                contentScale = ContentScale.Crop
            )
            }
            recipe.title?.let {
                    title ->
                Row(
                    modifier= Modifier
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
                        style = MaterialTheme.typography.h3,
                    )
                    Text(
                        text = recipe.rating.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h4,
                    )
                }
            }
        }
        ConstraintLayout() {
            val heartButton = createRef()
            Surface( modifier = Modifier.constrainAs(heartButton) {
                end.linkTo(parent.end)
            }) {
                AnimatedHeartButton(
                    buttonState = buttonState.value,
                    onClick = {
                        buttonState.value = buttonState.value != true
                    }
                )
            }
        }

    }
}