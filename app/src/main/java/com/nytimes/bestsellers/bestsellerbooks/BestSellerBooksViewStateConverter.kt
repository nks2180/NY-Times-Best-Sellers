package com.nytimes.bestsellers.bestsellerbooks

import com.nytimes.bestsellers.bestsellerbooks.api.ApiBestSellers
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBookViewState
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBooksViewState
import com.nytimes.bestsellers.common.ErrorType
import com.nytimes.bestsellers.common.rx.scheduler.Converter

class BestSellerBooksViewStateConverter : Converter<ApiBestSellers, BestSellerBooksViewState> {
    override fun apply(apiBestSellers: ApiBestSellers): BestSellerBooksViewState {
        if (apiBestSellers.status == "OK" && !apiBestSellers.books.isNullOrEmpty()) {
            val books = mutableListOf<BestSellerBookViewState>()
            for (apiBestSeller in apiBestSellers.books) {
                val apiBookInfo = apiBestSeller.bookDetails[0]
                val book = BestSellerBookViewState(
                    title = apiBookInfo.title,
                    description = apiBookInfo.description,
                    author = apiBookInfo.author,
                    contributor = apiBookInfo.contributor,
                    price = apiBookInfo.price
                )
                books.add(book)
            }
            return BestSellerBooksViewState.Data(books)
        }
        return BestSellerBooksViewState.Error(ErrorType.NO_DATA_FOUND)
    }

}

