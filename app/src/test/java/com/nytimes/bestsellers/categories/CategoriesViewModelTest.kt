package com.nytimes.bestsellers.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import com.nytimes.bestsellers.categories.viewstate.CategoriesState
import com.nytimes.bestsellers.categories.viewstate.CategoryComponentViewState
import com.nytimes.bestsellers.common.AppConstants
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
class CategoriesViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val bestSellersRepository: CategoriesRepository = Mockito.mock(CategoriesRepository::class.java)

    @Mock
    private lateinit var liveData: MutableLiveData<List<CategoryComponentViewState>>

    private val savedStateHandle: SavedStateHandle = Mockito.mock(SavedStateHandle::class.java)
    private val categoryOne: CategoryComponentViewState = Mockito.mock(CategoryComponentViewState::class.java)
    private val categoryTwo: CategoryComponentViewState = Mockito.mock(CategoryComponentViewState::class.java)
    private val categoryThree: CategoryComponentViewState = Mockito.mock(CategoryComponentViewState::class.java)
    private val categoryFour: CategoryComponentViewState = Mockito.mock(CategoryComponentViewState::class.java)

    private lateinit var viewModel: CategoriesViewModel

    @Before
    fun setUp() {
        viewModel = CategoriesViewModel(bestSellersRepository, savedStateHandle)
    }

    @Test
    fun `should get value from saved state if data saved into savedStateHandle`() {
        whenever(savedStateHandle.contains(AppConstants.KEY_CATEGORIES)).thenReturn(true)
        whenever(liveData.value).thenReturn(listOf(categoryOne, categoryTwo, categoryFour))
        whenever(savedStateHandle.getLiveData<List<CategoryComponentViewState>>(AppConstants.KEY_CATEGORIES)).thenReturn(liveData)

        viewModel.fetchCategories()
        val testObserver = viewModel.categories().testObserver()

        val expectedViewState = CategoriesState.Data(listOf(categoryOne, categoryTwo, categoryFour))
        Truth.assertThat(testObserver.observedValues[0]).isEqualTo(expectedViewState)
    }

    @Test
    fun `should get value from API if no data saved into savedStateHandle`() {
        val data = CategoriesState.Data(listOf(categoryFour, categoryOne, categoryThree, categoryTwo))
        whenever(savedStateHandle.contains(AppConstants.KEY_CATEGORIES)).thenReturn(false)
        whenever(bestSellersRepository.fetchCategories()).thenReturn(Observable.just(data))

        viewModel.fetchCategories()
        val testObserver = viewModel.categories().testObserver()

        val expectedViewState = CategoriesState.Data(listOf(categoryFour, categoryOne, categoryThree, categoryTwo))
        Truth.assertThat(testObserver.observedValues[0]).isEqualTo(expectedViewState)
    }
}
