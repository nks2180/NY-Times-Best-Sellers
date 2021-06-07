package com.nytimes.bestsellers.common.rx.scheduler

import io.reactivex.*

class SchedulingStrategy<T> constructor(private val subscribingScheduler: Scheduler,
                                        private val observingScheduler: Scheduler) :
        ObservableTransformer<T, T>, SingleTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .subscribeOn(subscribingScheduler)
                .observeOn(observingScheduler)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream
                .subscribeOn(subscribingScheduler)
                .observeOn(observingScheduler)
    }
}
