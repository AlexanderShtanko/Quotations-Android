package com.alexandershtanko.quotations.viewmodels;

import com.alexandershtanko.quotations.domain.interactor.AddQuotationUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;
import com.alexandershtanko.quotations.utils.mvvm.RxViewModel;

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
    private final AddQuotationUseCase addQuotationUseCase;
    private final RemoveQuotationUseCase removeQuotationUseCase;
    private PublishSubject<String> addInstrumentSubject=PublishSubject.create();
    private PublishSubject<String> removeInstrumentSubject=PublishSubject.create();

    @Inject
    public InstrumentsViewModel(GetInstrumentsUseCase getInstrumentsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, AddQuotationUseCase addQuotationUseCase, RemoveQuotationUseCase removeQuotationUseCase) {
        this.getInstrumentsUseCase = getInstrumentsUseCase;
        this.getSelectedInstrumentsUseCase = getSelectedInstrumentsUseCase;
        this.addQuotationUseCase = addQuotationUseCase;
        this.removeQuotationUseCase = removeQuotationUseCase;
    }

    @Override
    protected void onSubscribe(CompositeDisposable s) {
        s.add(addInstrumentSubject.flatMap(instrument->))
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

    public Observable<Boolean> removeInstrument(String> instrument) {
        return removeQuotationUseCase.execute(new RemoveQuotationUseCase.Params(instrument));

    }

}
