package com.example.samplerecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.samplerecipeapp.presentation.BaseApplication
import com.example.samplerecipeapp.presentation.components.*
import com.example.samplerecipeapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val loading = viewModel.loading.value
                    val scaffoldState = rememberScaffoldState()
                    val page = viewModel.page.value

                    Scaffold (
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                           scaffoldState.snackbarHostState
                        },
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                },
                                scrollPosition = viewModel.categoryScrollPosition,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onChangeCategoryChangedPosition = viewModel::onChangeCategoryChangedPosition,
                                onToggleTheme = { application.toggleDarkTheme() }
                            )
                        }
                    ) {
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            page = page,
                            onChangeRecipeListScrollPosition = viewModel::onChangeRecipeListScrollPosition,
                            onTriggerEvent = viewModel::onTriggerEvent,
                            scaffoldState = scaffoldState,
                            navController = findNavController()
                        )
                    }

                }
            }
        }
    }

}