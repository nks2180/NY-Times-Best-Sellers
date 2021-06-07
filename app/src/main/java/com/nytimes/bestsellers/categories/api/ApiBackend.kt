package com.nytimes.bestsellers.categories.api

import com.nytimes.bestsellers.bestsellerbooks.api.ApiBestSellers
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiBackend {

    @GET("svc/books/v3/lists/names.json")
    fun fetchCategories(@Query("api-key") apiKey: String): Single<ApiCategories>

    @GET("svc/books/v3/lists.json")
    fun fetchBestSellers(@Query("api-key") apiKey: String,
                         @Query("list") category: String): Single<ApiBestSellers>

}
