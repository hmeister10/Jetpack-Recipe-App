package com.example.samplerecipeapp.network.model

import com.example.samplerecipeapp.domain.model.Recipe
import com.example.samplerecipeapp.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {

    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            featuredImage = model.featured_image,
            publisher =  model.publisher,
            rating = model.rating,
            sourceUrl = model.source_url,
            description = model.description,
            cookingInstructions = model.cooking_instructions,
            ingredients = model.ingredients?: listOf(),
            dateAdded = model.date_added,
            dateUpdated = model.date_updated
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            pk = domainModel.id,
            title = domainModel.title,
            publisher =  domainModel.publisher,
            rating = domainModel.rating,
            source_url = domainModel.sourceUrl,
            description = domainModel.description,
            cooking_instructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients?: listOf(),
            date_added = domainModel.dateAdded,
            date_updated = domainModel.dateUpdated
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map { mapFromDomainModel(it)}
    }
}