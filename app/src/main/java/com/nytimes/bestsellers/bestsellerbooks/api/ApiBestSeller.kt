package com.nytimes.bestsellers.bestsellerbooks.api

import com.squareup.moshi.Json

data class ApiBestSeller(
    @field:Json(name = "list_name")
    val listName: String,
    @field:Json(name = "display_name")
    val name: String,
    @field:Json(name = "bestsellers_date")
    val bestsellersDate: String,
    @field:Json(name = "published_date")
    val publishedDate: String,
    @field:Json(name = "book_details")
    val bookDetails: List<BookInfo>
)

data class BookInfo(
    val title: String,
    val description: String,
    val author: String,
    val contributor: String,
    val price: String
)
