package com.nytimes.bestsellers.bestsellerbooks.viewstate

import com.nytimes.bestsellers.common.ErrorType

sealed class BestSellerBooksViewState {
    object Loading: BestSellerBooksViewState()

    data class Data(val bestSellers: List<BestSellerBookViewState>): BestSellerBooksViewState()

    data class Error(val error: ErrorType) : BestSellerBooksViewState()
}
