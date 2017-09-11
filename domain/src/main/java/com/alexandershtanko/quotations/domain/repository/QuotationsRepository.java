package com.alexandershtanko.quotations.domain.repository;

import com.alexandershtanko.quotations.domain.interactor.SubscribeUseCase;
import com.alexandershtanko.quotations.domain.interactor.UnsubscribeUseCase;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 05/09/2017.
 *
 */

public interface QuotationsRepository {
    Observable<List<Quotation>> getQuotations();
    Observable<Boolean> addInstruments(SubscribeUseCase.Params params);
    Observable<Boolean> removeInstruments(UnsubscribeUseCase.Params params);
    Observable<List<String>> getSelectedInstruments();
    Observable<Boolean> getConnectionState();
    Observable<List<String>> getInstruments();
}
