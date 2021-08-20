package com.example.samplerecipeapp.repository

import com.example.samplerecipeapp.domain.model.Recipe
import com.example.samplerecipeapp.network.RecipeService
import com.example.samplerecipeapp.network.model.RecipeDtoMapper

class RecipeRepositoryImpl (
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository{
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = recipeService.search(token, page, query).recipes
        return mapper.toDomainList(result)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        val result = recipeService.get(token, id)
        return mapper.mapToDomainModel(result)
    }
}