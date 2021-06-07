package com.nytimes.bestsellers.categories.api

import com.squareup.moshi.Json

data class ApiCategory(
    @field:Json(name = "list_name")
    val listName: String,
    @field:Json(name = "display_name")
    val displayName: String,
    @field:Json(name = "list_name_encoded")
    val listNameEncoded: String,
    @field:Json(name = "oldest_published_date")
    val olderPublishedDate: String,
    @field:Json(name = "newest_published_date")
    val newestPublishedDate: String,
    @field:Json(name = "updated")
    val updated: String
)
