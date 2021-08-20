package com.example.samplerecipeapp.network.responses

import com.example.samplerecipeapp.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse (
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeDto>,
)