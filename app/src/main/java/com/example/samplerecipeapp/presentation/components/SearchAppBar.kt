package com.example.samplerecipeapp.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.samplerecipeapp.presentation.ui.recipe_list.FoodCategory
import com.example.samplerecipeapp.presentation.ui.recipe_list.getAllFoodCategories
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    scrollPosition: Int,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryChangedPosition: (Int) -> Unit,
    onToggleTheme: () -> Unit

) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            ) {
                val focusManager = LocalFocusManager.current
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .padding(8.dp)
                        .background(color= MaterialTheme.colors.background),
                    textStyle= MaterialTheme.typography.button,
                    value = query,
                    onValueChange = { newValue ->
                        onQueryChanged(newValue)
                    },
                    singleLine = true,
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Ascii,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onExecuteSearch()
                            focusManager.clearFocus()
                        }
                    ),
                )
                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val (menu) = createRefs()
                    IconButton(
                        modifier = Modifier
                            .constrainAs(menu) {
                                end.linkTo(parent.end)
                                linkTo(top = parent.top, bottom = parent.bottom)
                            },
                        onClick = onToggleTheme,
                    ){
                        Icon(Icons.Filled.MoreVert, "more")
                    }
                }
            }
            val scrollState = rememberScrollState()
            val scope = rememberCoroutineScope()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .horizontalScroll(scrollState),
            ) {
                scope.launch { scrollState.scrollTo(scrollPosition) }
                for (category in getAllFoodCategories()) {
                    Chip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                            onChangeCategoryChangedPosition(scrollState.value)
                        },
                        onExecuteSearch = {
                            onExecuteSearch
                        }
                    )
                }
            }
        }

    }
}