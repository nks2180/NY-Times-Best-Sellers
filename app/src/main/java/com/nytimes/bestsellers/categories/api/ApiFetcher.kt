package com.nytimes.bestsellers.categories.api

import com.nytimes.bestsellers.bestsellerbooks.api.ApiBestSellers
import com.nytimes.bestsellers.common.AppConstants
import io.reactivex.Single

class ApiFetcher(private val apiBackend: ApiBackend) {

    fun fetchCategories(): Single<ApiCategories> {
        return apiBackend.fetchCategories(AppConstants.API_KEY)
    }

    fun fetchBestSellerBooks(category: String): Single<ApiBestSellers> {
        return apiBackend.fetchBestSellers(apiKey = AppConstants.API_KEY, category = category)
    }
}
