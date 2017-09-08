package com.alexandershtanko.quotations.viewmodels;

import com.alexandershtanko.quotations.domain.interactor.GetConnectionStateUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;
import com.alexandershtanko.quotations.domain.models.Quotation;
import com.alexandershtanko.quotations.utils.mvvm.RxViewModel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 08/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationsViewModel extends RxViewModel {
    private final GetQuotationsUseCase getQuotationUseCase;
    private final RemoveQuotationUseCase removeQuotationUseCase;
    private final GetConnectionStateUseCase getConnectionStateUseCase;
    private final GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase;

    @Inject
    public QuotationsViewModel(GetQuotationsUseCase getQuotationsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, RemoveQuotationUseCase removeQuotationUseCase, GetConnectionStateUseCase getConnectionStateUseCase) {
        this.getQuotationUseCase = getQuotationsUseCase;
        this.removeQuotationUseCase = removeQuotationUseCase;
        this.getConnectionStateUseCase = getConnectionStateUseCase;
        this.getSelectedInstrumentsUseCase = getSelectedInstrumentsUseCase;
    }

    @Override
    protected void onSubscribe(CompositeDisposable s) {
//        s.add(getSelectedInstrumentsUseCase.execute().first(new ArrayList<>()).subscribe());
    }

    public Observable<List<Quotation>> getQuotations() {
        return getQuotationUseCase.execute();
    }

    public Observable<Boolean> removeQuotation(String symbol) {
        return removeQuotationUseCase.execute(new RemoveQuotationUseCase.Params(Arrays.asList(symbol)));
    }

    public Observable<Boolean> getConnectionState() {
        return getConnectionStateUseCase.execute();
    }
}
