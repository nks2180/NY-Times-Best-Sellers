package com.nytimes.bestsellers.categories

import com.nhaarman.mockitokotlin2.whenever
import com.nytimes.bestsellers.categories.api.ApiCategories
import com.nytimes.bestsellers.categories.api.ApiFetcher
import com.nytimes.bestsellers.categories.viewstate.CategoriesState
import com.nytimes.bestsellers.categories.viewstate.CategoriesViewStateConverter
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
class CategoriesRepositoryTest {
    @Mock
    private lateinit var apiCategories: ApiCategories

    @Mock
    private lateinit var categoriesState: CategoriesState

    @Mock
    private lateinit var apiFetcher: ApiFetcher

    @Mock
    private lateinit var viewStateConverter: CategoriesViewStateConverter

    private val loadingViewState = CategoriesState.Loading

    private lateinit var repository: CategoriesRepository


    @Before
    fun setUp() {
        repository = CategoriesRepository(apiFetcher, viewStateConverter, TestSchedulingStrategyFactory.immediate())
    }

    @Test
    fun `should return correct categories ViewState when fetchCategories is called`() {
        whenever(apiFetcher.fetchCategories()).thenReturn(Single.just(apiCategories))
        whenever(viewStateConverter.apply(apiCategories)).thenReturn(categoriesState)
        val observer = repository.fetchCategories().test()

        observer.assertValues(loadingViewState, categoriesState)
    }

    @Test
    fun `should emit error view state while getting error fetching best sellers`() {
        whenever(apiFetcher.fetchCategories()).thenReturn(Single.error(JsonEncodingException("json error")))
        val observer = repository.fetchCategories().test()

        val errorViewState = CategoriesState.Error(ErrorType.UNKNOWN)
        observer.assertValues(loadingViewState, errorViewState)
    }
}
