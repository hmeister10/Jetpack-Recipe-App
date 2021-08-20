package com.example.samplerecipeapp.presentation.components

import android.text.style.ClickableSpan
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.samplerecipeapp.R


@ExperimentalAnimationApi
@Composable
fun AnimatedHeartButton(
    buttonState: Boolean,
    onClick: () -> Unit
) {

    val resource: Painter = if (buttonState) {
        painterResource(id = R.drawable.heart_grey)
    } else {
        painterResource(id = R.drawable.heart_red)
    }

    Image(
        modifier = Modifier
            .width(35.dp)
            .height(35.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        painter = resource,
        contentDescription = "animated image",
    )
}
