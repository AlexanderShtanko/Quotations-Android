package com.alexandershtanko.quotations.viewmodels;

import com.alexandershtanko.quotations.data.utils.ErrorUtils;
import com.alexandershtanko.quotations.domain.interactor.GetConnectionStateUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.SubscribeUseCase;
import com.alexandershtanko.quotations.domain.interactor.UnsubscribeUseCase;
import com.alexandershtanko.quotations.domain.interactor.UpdateSortUseCase;
import com.alexandershtanko.quotations.domain.models.Quotation;
import com.alexandershtanko.quotations.utils.mvvm.RxViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 08/09/2017.
 */

public class QuotationsViewModel extends RxViewModel {
    private final GetQuotationsUseCase getQuotationUseCase;
    private final UnsubscribeUseCase unsubscribeUseCase;
    private final GetConnectionStateUseCase getConnectionStateUseCase;
    private final GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase;
    private final UpdateSortUseCase updateSortUseCase;
    private final SubscribeUseCase subscribeUseCase;

    @Inject
    public QuotationsViewModel(GetQuotationsUseCase getQuotationsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, UnsubscribeUseCase unsubscribeUseCase, GetConnectionStateUseCase getConnectionStateUseCase, SubscribeUseCase subscribeUseCase,UpdateSortUseCase updateSortUseCase) {
        this.getQuotationUseCase = getQuotationsUseCase;
        this.unsubscribeUseCase = unsubscribeUseCase;
        this.getConnectionStateUseCase = getConnectionStateUseCase;
        this.getSelectedInstrumentsUseCase = getSelectedInstrumentsUseCase;
        this.subscribeUseCase = subscribeUseCase;
        this.updateSortUseCase=updateSortUseCase;
    }

    @Override
    protected void onSubscribe(CompositeDisposable s) {
        s.add(getConnectionState()
                .filter(state -> state)
                .switchMap(state -> getSelectedInstrumentsUseCase.execute().firstElement().toObservable())
                .switchMap(instruments -> subscribeUseCase.execute(new SubscribeUseCase.Params(instruments)))
                .subscribe(state -> {
                }, ErrorUtils::log));
    }

    public Observable<List<Quotation>> getQuotations() {
        return getQuotationUseCase.execute();
    }

    public Observable<Boolean> removeQuotation(String symbol) {
        return unsubscribeUseCase.execute(new UnsubscribeUseCase.Params(Arrays.asList(symbol)));
    }

    public Observable<Boolean> getConnectionState() {
        return getConnectionStateUseCase.execute();
    }

    public void updateSort(List<Quotation> list) {
        List<String> keys = new ArrayList<>();
        for (Quotation item : list) {
            keys.add(item.getSymbol());
        }
        updateSortUseCase.execute(keys);
    }
}
