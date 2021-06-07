package com.nytimes.bestsellers.navigation

interface Navigator {

    fun goToDetailsPage(encodedCategory: String, categoryName: String)

}
