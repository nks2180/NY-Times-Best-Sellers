package com.nytimes.bestsellers.categories.di

import androidx.lifecycle.SavedStateHandle
import com.nytimes.bestsellers.categories.CategoriesAdapter
import com.nytimes.bestsellers.categories.CategoriesRepository
import com.nytimes.bestsellers.categories.CategoriesViewModel
import com.nytimes.bestsellers.categories.viewstate.CategoriesViewStateConverter
import com.nytimes.bestsellers.common.rx.scheduler.AndroidSchedulingStrategyFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoriesModule = module {

    factory { CategoriesViewStateConverter() }
    factory { CategoriesRepository(get(), get(), AndroidSchedulingStrategyFactory.io()) }

    /**
     * ViewModel
     */

    viewModel { (handle: SavedStateHandle) ->
        CategoriesViewModel(
            get(),
            handle
        )
    }
   // factory { CategoriesViewModel(get()) }

    /**
     * RecyclerView Adapter
     */
    factory { CategoriesAdapter() }
}
