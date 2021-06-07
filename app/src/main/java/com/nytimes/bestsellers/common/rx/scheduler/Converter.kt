package com.nytimes.bestsellers.common.rx.scheduler

import io.reactivex.functions.Function

interface Converter<T, R> : Function<T, R>
