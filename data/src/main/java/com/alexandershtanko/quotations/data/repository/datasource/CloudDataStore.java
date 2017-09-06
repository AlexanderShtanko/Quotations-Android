package com.alexandershtanko.quotations.data.repository.datasource;

import com.alexandershtanko.quotations.data.models.QuotationEntity;
import com.alexandershtanko.quotations.domain.interactor.AddQuotationUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public interface CloudDataStore {
    Observable<List<QuotationEntity>> getQuotations();
    Observable<AddQuotationUseCase.Result> addSubscription(List<String> instruments);
    Observable<RemoveQuotationUseCase.Result> removeSubscription(List<String> instruments);
}
