package com.example.samplerecipeapp.di

import com.example.samplerecipeapp.network.RecipeService
import com.example.samplerecipeapp.network.model.RecipeDtoMapper
import com.example.samplerecipeapp.repository.RecipeRepository
import com.example.samplerecipeapp.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, recipeDtoMapper)
    }
}