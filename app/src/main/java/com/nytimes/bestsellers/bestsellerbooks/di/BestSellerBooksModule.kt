package com.nytimes.bestsellers.bestsellerbooks.di

import com.nytimes.bestsellers.bestsellerbooks.BestSellerBooksViewStateConverter
import com.nytimes.bestsellers.bestsellerbooks.BestSellersAdapter
import com.nytimes.bestsellers.bestsellerbooks.BestSellerBooksRepository
import com.nytimes.bestsellers.bestsellerbooks.BestSellerBooksViewModel
import com.nytimes.bestsellers.common.rx.scheduler.AndroidSchedulingStrategyFactory
import org.koin.dsl.module
import androidx.lifecycle.SavedStateHandle
import org.koin.androidx.viewmodel.dsl.viewModel

val bestSellerBooksModule = module {

    factory { BestSellerBooksViewStateConverter() }
    factory { BestSellerBooksRepository(get(), get(), AndroidSchedulingStrategyFactory.io()) }

    /**
     * ViewModel
     */
    viewModel { (handle: SavedStateHandle) ->
        BestSellerBooksViewModel(
            get(),
            handle
        )
    }

        /**
         * RecyclerView Adapter
         */
        factory { BestSellersAdapter() }
    }
