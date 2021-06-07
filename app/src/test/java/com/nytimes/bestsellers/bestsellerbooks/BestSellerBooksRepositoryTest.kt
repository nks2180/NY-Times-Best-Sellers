package com.nytimes.bestsellers.bestsellerbooks

import com.nhaarman.mockitokotlin2.whenever
import com.nytimes.bestsellers.bestsellerbooks.api.ApiBestSellers
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBooksViewState
import com.nytimes.bestsellers.categories.api.ApiFetcher
import com.nytimes.bestsellers.common.ErrorType
import com.nytimes.bestsellers.rx.TestSchedulingStrategyFactory
import com.squareup.moshi.JsonEncodingException
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BestSellerBooksRepositoryTest{

    @Mock
    private lateinit var apiBestSellers: ApiBestSellers
    @Mock
    private lateinit var bestSellerBooksState: BestSellerBooksViewState
    @Mock
    private lateinit var apiFetcher: ApiFetcher
    @Mock
    private lateinit var viewStateConverter: BestSellerBooksViewStateConverter

    private val categoryID = "some category ID"
    private val loadingViewState = BestSellerBooksViewState.Loading

    private lateinit var repository: BestSellerBooksRepository

    @Before
    fun setUp() {
        repository = BestSellerBooksRepository(apiFetcher, viewStateConverter,TestSchedulingStrategyFactory.immediate())
    }

    @Test
    fun `should return correct reviewsViewState when getReviews is called`() {
        whenever(apiFetcher.fetchBestSellerBooks(categoryID)).thenReturn(Single.just(apiBestSellers))
        whenever(viewStateConverter.apply(apiBestSellers)).thenReturn(bestSellerBooksState)
        val observer = repository.fetchBestSellerBooks(categoryID).test()

        observer.assertValues(loadingViewState, bestSellerBooksState)
    }

    @Test
    fun `should emit error view state while getting error fetching best sellers`() {
        whenever(apiFetcher.fetchBestSellerBooks(categoryID)).thenReturn(Single.error(JsonEncodingException("json error")))
        val observer = repository.fetchBestSellerBooks(categoryID).test()

        val errorViewState = BestSellerBooksViewState.Error(ErrorType.UNKNOWN)
        observer.assertValues(loadingViewState, errorViewState)
    }
}
