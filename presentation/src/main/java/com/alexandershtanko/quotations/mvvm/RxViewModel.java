package com.alexandershtanko.quotations.mvvm;


import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by aleksandr on 12.06.16.
 */
public abstract class RxViewModel implements ViewModel {
    BehaviorSubject<Throwable> errorSubject = BehaviorSubject.create();
    CompositeDisposable s = new CompositeDisposable();

    public void onError(Throwable throwable) {
        errorSubject.onNext(throwable);
    }

    @Override
    public void subscribe() {
        s = new CompositeDisposable();
        onSubscribe(s);
    }

    @Override
    public void dispose() {
        s.dispose();
        s = null;
    }


    public void saveInstanceState() {
    }

    public void restoreInstanceState() {
    }

    protected abstract void onSubscribe(CompositeDisposable s);

    public Boolean notNull(Object object) {
        return object != null;
    }

    public Observable<Throwable> getErrorObservable() {
        return errorSubject.filter(this::notNull);
    }

    public void clearError() {
        errorSubject.onNext(null);
    }
}
