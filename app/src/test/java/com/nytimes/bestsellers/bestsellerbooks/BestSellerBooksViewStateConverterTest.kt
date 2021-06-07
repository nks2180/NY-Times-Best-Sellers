package com.nytimes.bestsellers.bestsellerbooks

import com.google.common.truth.Truth.assertThat
import com.nytimes.bestsellers.bestsellerbooks.api.ApiBestSeller
import com.nytimes.bestsellers.bestsellerbooks.api.ApiBestSellers
import com.nytimes.bestsellers.bestsellerbooks.api.BookInfo
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBookViewState
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBooksViewState
import com.nytimes.bestsellers.common.ErrorType
import org.junit.Before
import org.junit.Test

class BestSellerBooksViewStateConverterTest {

    private val bestSellerListName = "List Name"
    private val bestSellerDisplayName = "Display Name"
    private val bestsellersDate = "2021-01-01"
    private val publishedDate = "2020-01-01"
    private val bookInfoOne = BookInfo(
        title = "Book title 1",
        description = "Book description 1",
        author = "Book author 1",
        contributor = "Book Contributor 1",
        price = "Book Price 1"
    )
    private val bookInfoAnother = BookInfo(
        title = "Book title 2",
        description = "Book description 2",
        author = "Book author 2",
        contributor = "Book Contributor 2",
        price = "Book Price 2"
    )
    private val bookInfoViewStateOne = BestSellerBookViewState(
        title = "Book title 1",
        description = "Book description 1",
        author = "Book author 1",
        contributor = "Book Contributor 1",
        price = "Book Price 1"
    )
    private val bookInfoViewStateAnother = BestSellerBookViewState(
        title = "Book title 2",
        description = "Book description 2",
        author = "Book author 2",
        contributor = "Book Contributor 2",
        price = "Book Price 2"
    )

    private lateinit var converter: BestSellerBooksViewStateConverter

    @Before
    fun setUp() {
        converter = BestSellerBooksViewStateConverter()
    }

    @Test
    fun `should return no data found when best sellers is empty`() {
        val apiBestSellers = ApiBestSellers(
            status = "OK",
            resultCount = 0,
            copyright = "Copyright text",
            books = emptyList()
        )
        val convertedViewState = converter.apply(apiBestSellers)

        val expectedViewState = BestSellerBooksViewState.Error(ErrorType.NO_DATA_FOUND)
        assertThat(convertedViewState).isEqualTo(expectedViewState)
    }

    @Test
    fun `should return no data found when api status is not OK`() {
        val apiBestSellers = ApiBestSellers(
            status = "ERROR",
            resultCount = 0,
            copyright = "Copyright text",
            books = emptyList()
        )
        val convertedViewState = converter.apply(apiBestSellers)

        val expectedViewState = BestSellerBooksViewState.Error(ErrorType.NO_DATA_FOUND)
        assertThat(convertedViewState).isEqualTo(expectedViewState)
    }


    @Test
    fun `should return view states when status is OK`() {
        val apiBestSellerOne = bookViewStates(bookInfoOne)
        val apiBestSellerAnother = bookViewStates(bookInfoAnother, bookInfoOne)
        val apiBestSellerAnotherOne = bookViewStates(bookInfoOne)
        val apiBestSellers = ApiBestSellers(
            status = "OK",
            resultCount = 0,
            copyright = "Copyright text",
            books = listOf(apiBestSellerOne, apiBestSellerAnother, apiBestSellerOne, apiBestSellerAnotherOne)
        )
        val convertedViewState = converter.apply(apiBestSellers)


        val expectedViewState = bookViewStates(
            bookInfoViewStateOne,
            bookInfoViewStateAnother,
            bookInfoViewStateOne,
            bookInfoViewStateOne
        )
        assertThat(convertedViewState).isEqualTo(expectedViewState)
    }

    private fun bookViewStates(vararg bookInfo: BookInfo): ApiBestSeller {
        return ApiBestSeller(
            listName = bestSellerListName,
            name = bestSellerDisplayName,
            bestsellersDate = bestsellersDate,
            publishedDate = publishedDate,
            bookDetails = bookInfo.toList()
        )
    }

    private fun bookViewStates(vararg bookViewStates: BestSellerBookViewState): BestSellerBooksViewState.Data {
        return BestSellerBooksViewState.Data(bookViewStates.toList())
    }
}
