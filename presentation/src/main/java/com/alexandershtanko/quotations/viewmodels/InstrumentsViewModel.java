package com.alexandershtanko.quotations.viewmodels;

import com.alexandershtanko.quotations.data.utils.ErrorUtils;
import com.alexandershtanko.quotations.domain.interactor.GetInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.SubscribeUseCase;
import com.alexandershtanko.quotations.domain.interactor.UnsubscribeUseCase;
import com.alexandershtanko.quotations.utils.mvvm.RxViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 08/09/2017.
 *         Copyright Ostrovok.ru
 */

public class InstrumentsViewModel extends RxViewModel {

    private final GetInstrumentsUseCase getInstrumentsUseCase;
    private final GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase;
    private final SubscribeUseCase subscribeUseCase;
    private final UnsubscribeUseCase unsubscribeUseCase;
    private PublishSubject<String> addInstrumentSubject = PublishSubject.create();
    private PublishSubject<String> removeInstrumentSubject = PublishSubject.create();

    private PublishSubject<Boolean> addResultSubject = PublishSubject.create();
    private PublishSubject<Boolean> removeResultSubject = PublishSubject.create();


    @Inject
    public InstrumentsViewModel(GetInstrumentsUseCase getInstrumentsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, SubscribeUseCase subscribeUseCase, UnsubscribeUseCase unsubscribeUseCase) {
        this.getInstrumentsUseCase = getInstrumentsUseCase;
        this.getSelectedInstrumentsUseCase = getSelectedInstrumentsUseCase;
        this.subscribeUseCase = subscribeUseCase;
        this.unsubscribeUseCase = unsubscribeUseCase;
    }

    @Override
    protected void onSubscribe(CompositeDisposable s) {
        s.add(addInstrumentSubject.hide()
                .flatMap(instrument -> subscribeUseCase.execute(new SubscribeUseCase.Params(Collections.singletonList(instrument))))
                .subscribe(addResultSubject::onNext, ErrorUtils::log));

        s.add(removeInstrumentSubject.hide()
                .flatMap(instrument -> unsubscribeUseCase.execute(new UnsubscribeUseCase.Params(Collections.singletonList(instrument))))
                .subscribe(removeResultSubject::onNext, ErrorUtils::log));
    }

    public Observable<List<String>> getInstruments() {
        return getInstrumentsUseCase.execute();
    }

    public Observable<List<String>> getSelectedInstruments() {
        return getSelectedInstrumentsUseCase.execute();
    }

    public void addInstrument(String instrument) {
        addInstrumentSubject.onNext(instrument);
    }

    public void removeInstrument(String instrument) {
        removeInstrumentSubject.onNext(instrument);
    }

    public Observable<Boolean> getAddResult() {
        return addResultSubject.hide();
    }

    public Observable<Boolean> getRemoveResult() {
        return removeResultSubject.hide();
    }
}
