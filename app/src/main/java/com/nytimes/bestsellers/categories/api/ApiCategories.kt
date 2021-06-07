package com.nytimes.bestsellers.categories.api

import com.squareup.moshi.Json

data class ApiCategories(
    val status: String,
    @field:Json(name = "num_results")
    val resultCount: Int,
    val copyright: String,
    @field:Json(name = "results")
    val bestSellers: List<ApiCategory>
)
