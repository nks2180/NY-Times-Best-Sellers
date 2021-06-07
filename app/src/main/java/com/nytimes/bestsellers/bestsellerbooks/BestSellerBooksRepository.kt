package com.nytimes.bestsellers.bestsellerbooks

import com.nytimes.bestsellers.bestsellerbooks.viewstate.BestSellerBooksViewState
import com.nytimes.bestsellers.common.ErrorType
import com.nytimes.bestsellers.common.rx.scheduler.SchedulingStrategyFactory
import com.nytimes.bestsellers.categories.api.ApiFetcher
import com.squareup.moshi.JsonEncodingException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.net.UnknownHostException

class BestSellerBooksRepository(private val fetcher: ApiFetcher,
                                private val converter: BestSellerBooksViewStateConverter,
                                private val schedulingStrategyFactory: SchedulingStrategyFactory) {

    fun fetchBestSellerBooks(categoryName: String): Observable<BestSellerBooksViewState> {
        return fetcher.fetchBestSellerBooks(categoryName)
            .map(converter)
            .toObservable()
            .startWith(BestSellerBooksViewState.Loading)
            .compose(ErrorConverter())
            .compose(schedulingStrategyFactory.create())
    }

   inner class ErrorConverter : ObservableTransformer<BestSellerBooksViewState, BestSellerBooksViewState> {

        override fun apply(upstream: Observable<BestSellerBooksViewState>): ObservableSource<BestSellerBooksViewState> {
            return upstream.onErrorResumeNext(Function<Throwable, ObservableSource<BestSellerBooksViewState>> { cause ->
                Observable.just(convertToCause(cause))
            })
        }

        private fun convertToCause(cause: Throwable): BestSellerBooksViewState {
            return when (cause) {
                is JsonEncodingException -> BestSellerBooksViewState.Error(ErrorType.UNKNOWN)
                is UnknownHostException -> BestSellerBooksViewState.Error(ErrorType.NO_INTERNET_CONNECTION)
                is HttpException -> BestSellerBooksViewState.Error(ErrorType.SERVER)
                else -> BestSellerBooksViewState.Error(ErrorType.UNKNOWN)

            }
        }
    }
}


