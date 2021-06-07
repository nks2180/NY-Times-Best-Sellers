package com.nytimes.bestsellers.categories

import com.nytimes.bestsellers.common.ErrorType
import com.nytimes.bestsellers.common.rx.scheduler.SchedulingStrategyFactory
import com.nytimes.bestsellers.categories.api.ApiFetcher
import com.nytimes.bestsellers.categories.viewstate.CategoriesState
import com.nytimes.bestsellers.categories.viewstate.CategoriesViewStateConverter
import com.squareup.moshi.JsonEncodingException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.net.UnknownHostException

class CategoriesRepository(private val fetcher: ApiFetcher,
                           private val converter: CategoriesViewStateConverter,
                           private val schedulingStrategyFactory: SchedulingStrategyFactory) {


    fun fetchCategories(): Observable<CategoriesState> {
        return fetcher.fetchCategories()
            .map(converter)
            .toObservable()
            .startWith(CategoriesState.Loading)
            .compose(ErrorConverter())
            .compose(schedulingStrategyFactory.create())
    }

   inner class ErrorConverter : ObservableTransformer<CategoriesState, CategoriesState> {

        override fun apply(upstream: Observable<CategoriesState>): ObservableSource<CategoriesState> {
            return upstream.onErrorResumeNext(Function<Throwable, ObservableSource<CategoriesState>> { cause ->
                Observable.just(convertToCause(cause))
            })
        }

        private fun convertToCause(cause: Throwable): CategoriesState {
            return when (cause) {
                is JsonEncodingException -> CategoriesState.Error(ErrorType.UNKNOWN)
                is UnknownHostException -> CategoriesState.Error(ErrorType.NO_INTERNET_CONNECTION)
                is HttpException -> CategoriesState.Error(ErrorType.SERVER)
                else -> CategoriesState.Error(ErrorType.UNKNOWN)

            }
        }
    }
}


