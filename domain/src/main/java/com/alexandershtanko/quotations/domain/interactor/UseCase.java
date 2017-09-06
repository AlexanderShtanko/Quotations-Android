package com.alexandershtanko.quotations.domain.interactor;


import io.reactivex.Observable;
import io.reactivex.Scheduler;

public abstract class UseCase<T, Params> {
    private Scheduler subscriptionScheduler;

    UseCase(Scheduler subscriptionScheduler) {
        this.subscriptionScheduler = subscriptionScheduler;
    }

    abstract Observable<T> buildUseCaseObservable(Params params);

    public final Observable<T> execute(Params params) {
        return this.buildUseCaseObservable(params).subscribeOn(subscriptionScheduler);
    }

}