package com.nytimes.bestsellers.bestsellerbooks.api

import com.nytimes.bestsellers.categories.api.ApiCategory
import com.squareup.moshi.Json

data class ApiBestSellers(
    val status: String,
    @field:Json(name = "num_results")
    val resultCount: Int,
    val copyright: String,
    @field:Json(name = "results")
    val books: List<ApiBestSeller>
)
