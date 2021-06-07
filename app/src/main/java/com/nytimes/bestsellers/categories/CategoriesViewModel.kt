package com.nytimes.bestsellers.categories

import androidx.lifecycle.*
import com.nytimes.bestsellers.categories.viewstate.CategoriesState
import com.nytimes.bestsellers.categories.viewstate.CategoryComponentViewState
import com.nytimes.bestsellers.common.AppConstants
import com.nytimes.bestsellers.common.AppConstants.Companion.KEY_CATEGORIES
import io.reactivex.disposables.Disposables

class CategoriesViewModel(
    private val repository: CategoriesRepository,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private var disposable = Disposables.empty()
    private var categories: MutableLiveData<CategoriesState> = MutableLiveData()

    fun categories() = categories

    fun fetchCategories() {
        if (state.contains(KEY_CATEGORIES)){
            val categoriesState: MutableLiveData< List<CategoryComponentViewState>> = state.getLiveData(AppConstants.KEY_CATEGORIES)
            categories.postValue(CategoriesState.Data(categoriesState.value!!))
        }
        else{
            disposable = repository.fetchCategories()
                .subscribe({ response -> categories.postValue(response) },
                    { error -> print("error $error") })
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (null != categories.value && categories.value is CategoriesState.Data) {
            val dataToBeSaved = categories.value as CategoriesState.Data
            state.set(KEY_CATEGORIES, dataToBeSaved.categories)
        }
    }
}
