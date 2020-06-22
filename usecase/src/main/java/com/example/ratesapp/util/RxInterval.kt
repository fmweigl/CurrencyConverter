package com.example.ratesapp.util

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class RxInterval {

    fun interval(period: Long, timeUnit: TimeUnit): Observable<Long> =
        Observable.interval(period, timeUnit)

}