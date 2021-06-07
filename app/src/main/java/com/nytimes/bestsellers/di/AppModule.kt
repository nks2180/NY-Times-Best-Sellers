package com.nytimes.bestsellers.di

import com.nytimes.bestsellers.bestsellerbooks.di.bestSellerBooksModule
import com.nytimes.bestsellers.categories.di.categoriesModule
import com.nytimes.bestsellers.navigation.androidNavigatorModule

val nyTimesModule = listOf(
    networkModule,
    androidNavigatorModule,
    categoriesModule,
    bestSellerBooksModule,
)
