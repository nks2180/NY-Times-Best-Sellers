package com.nytimes.bestsellers.bestsellerbooks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBookViewState
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBooksViewState
import com.nytimes.bestsellers.common.AppConstants
import com.nytimes.bestsellers.common.AppConstants.Companion.KEY_BEST_SELLERS
import com.nytimes.bestsellers.rx.testObserver
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BestSellerBooksViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val bestSellersRepository: BestSellerBooksRepository = Mockito.mock(BestSellerBooksRepository::class.java)
    @Mock
    private lateinit var liveData: MutableLiveData< List<BestSellerBookViewState>>

    private val savedStateHandle: SavedStateHandle = Mockito.mock(SavedStateHandle::class.java)
    private val bestSellerBooksStateOne: BestSellerBookViewState = Mockito.mock(BestSellerBookViewState::class.java)
    private val bestSellerBooksStateTwo: BestSellerBookViewState = Mockito.mock(BestSellerBookViewState::class.java)
    private val bestSellerBooksStateThree: BestSellerBookViewState = Mockito.mock(BestSellerBookViewState::class.java)
    private val bestSellerViewStateFour: BestSellerBookViewState = Mockito.mock(BestSellerBookViewState::class.java)

    private val categoryID = "some category ID"

    private lateinit var viewModel: BestSellerBooksViewModel

    @Before
    fun setUp() {
        viewModel = BestSellerBooksViewModel(bestSellersRepository, savedStateHandle)
    }

    @Test
    fun `should get value from saved state if data saved into savedStateHandle`() {
        whenever(liveData.value).thenReturn(listOf(bestSellerBooksStateOne, bestSellerBooksStateTwo, bestSellerViewStateFour))
        whenever(savedStateHandle.getLiveData<List<BestSellerBookViewState>>(KEY_BEST_SELLERS)).thenReturn(liveData)
        whenever(savedStateHandle.contains(AppConstants.KEY_BEST_SELLERS)).thenReturn(true)

        viewModel.fetchBestSellerBooks(categoryID)
        val testObserver = viewModel.bestSellers().testObserver()

        val expectedViewState = BestSellerBooksViewState.Data(listOf(bestSellerBooksStateOne, bestSellerBooksStateTwo, bestSellerViewStateFour))
        Truth.assertThat(testObserver.observedValues[0]).isEqualTo(expectedViewState)
    }

    @Test
    fun `should get value from API if no data saved into savedStateHandle`() {
        val data = BestSellerBooksViewState.Data(listOf(bestSellerBooksStateTwo, bestSellerViewStateFour, bestSellerBooksStateOne))
        whenever(savedStateHandle.contains(AppConstants.KEY_BEST_SELLERS)).thenReturn(false)
        whenever(bestSellersRepository.fetchBestSellerBooks(categoryID)).thenReturn(Observable.just(data))

        viewModel.fetchBestSellerBooks(categoryID)
        val testObserver = viewModel.bestSellers().testObserver()

        val expectedViewState = BestSellerBooksViewState.Data(listOf(bestSellerBooksStateTwo, bestSellerViewStateFour, bestSellerBooksStateOne))
        Truth.assertThat(testObserver.observedValues[0]).isEqualTo(expectedViewState)
    }
}
