package com.alexandershtanko.quotations.mvvm;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by aleksandr on 21.08.16.
 */
public abstract class RxService extends Service {

    BehaviorSubject<Throwable> errorSubject = BehaviorSubject.create();
    CompositeDisposable s = new CompositeDisposable();


    public void onError(Throwable throwable) {
        errorSubject.onNext(throwable);
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

    public void subscribe() {
        s = new CompositeDisposable();
        onSubscribe(s);
    }

    public void dispose() {
        s.dispose();
        s = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispose();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }


}
