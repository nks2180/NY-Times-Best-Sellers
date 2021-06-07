package com.nytimes.bestsellers.common.rx.scheduler

import io.reactivex.Scheduler

open class SchedulingStrategyFactory constructor(
    private val subscribingScheduler: Scheduler,
    private val observingScheduler: Scheduler
) {
    fun <T> create(): SchedulingStrategy<T> {
        return SchedulingStrategy(subscribingScheduler, observingScheduler)
    }

    fun getSubscribingScheduler(): Scheduler {
        return subscribingScheduler
    }
}

