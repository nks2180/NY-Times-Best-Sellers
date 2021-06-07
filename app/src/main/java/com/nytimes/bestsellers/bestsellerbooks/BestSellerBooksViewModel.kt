package com.nytimes.bestsellers.bestsellerbooks

import androidx.lifecycle.*
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBookViewState
import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBooksViewState
import com.nytimes.bestsellers.common.AppConstants
import io.reactivex.disposables.Disposables

class BestSellerBooksViewModel(
    private val repository: BestSellerBooksRepository,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private var disposable = Disposables.empty()
    private var bestSellerBooks: MutableLiveData<BestSellerBooksViewState> = MutableLiveData()

    fun bestSellers() = bestSellerBooks

    fun fetchBestSellerBooks(categoryName: String) {
        if (state.contains(AppConstants.KEY_BEST_SELLERS)) {
            val bestSellersState: MutableLiveData<List<BestSellerBookViewState>> = state.getLiveData(AppConstants.KEY_BEST_SELLERS)
            bestSellerBooks.postValue(BestSellerBooksViewState.Data(bestSellersState.value!!))
        } else {
            disposable = repository.fetchBestSellerBooks(categoryName)
                .subscribe({ response -> bestSellerBooks.postValue(response) },
                    { error -> print("error $error") })
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (null != bestSellerBooks.value && bestSellerBooks.value is BestSellerBooksViewState.Data) {
            val dataToBeSaved = bestSellerBooks.value as BestSellerBooksViewState.Data
            state.set(AppConstants.KEY_BEST_SELLERS, dataToBeSaved.bestSellers)
        }
    }
}
