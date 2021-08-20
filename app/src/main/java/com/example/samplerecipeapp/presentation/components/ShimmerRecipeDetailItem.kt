package com.example.samplerecipeapp.presentation.components

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerRecipeDetailItem(
    colors: List<Color>,
    cardHeight: Dp,
){
    BoxWithConstraints() {

        val cardWidthPx = LocalDensity.current.run {maxWidth.toPx()}
        val cardHeightPx = LocalDensity.current.run {cardHeight.toPx()}

        val infiniteTransition = rememberInfiniteTransition()
        val xShimmerState = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = cardWidthPx,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    delayMillis = 300,
                    easing = LinearEasing
                )
            )
        )
        val yShimmerState = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = cardHeightPx,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3300,
                    delayMillis = 500,
                    easing = LinearEasing
                )
            )
        )
        val brush = Brush.linearGradient(
            colors,
            start = Offset(xShimmerState.value - 200f, yShimmerState.value - 200f),
            end = Offset(xShimmerState.value, yShimmerState.value),

        )
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                        .background(brush)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(brush)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(brush)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(brush)
                )
            }
        }
    }





}