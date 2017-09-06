package com.alexandershtanko.quotations.data.repository;

import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;
import com.alexandershtanko.quotations.data.repository.datasource.DbDataStore;
import com.alexandershtanko.quotations.domain.interactor.AddQuotationUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;
import com.alexandershtanko.quotations.domain.models.Quotation;
import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationsDataRepository implements QuotationsRepository {

    private final CloudDataStore cloudDataStore;
    private final DbDataStore dbDataStore;

    @Inject
    public QuotationsDataRepository(CloudDataStore cloudDataStore, DbDataStore dbDataStore) {
        this.cloudDataStore = cloudDataStore;
        this.dbDataStore = dbDataStore;
    }

    @Override
    public Observable<List<Quotation>> getQuotations(GetQuotationsUseCase.Params params) {
        return dbDataStore.getQuotations().doOnSubscribe().doOnDispose();
    }

    @Override
    public Observable<AddQuotationUseCase.Result> addInstruments(AddQuotationUseCase.Params params) {
        dbDataStore.addInstruments(params.getNames());
        return cloudDataStore.addSubscription(params.getNames());
    }

    @Override
    public Observable<RemoveQuotationUseCase.Result> removeInstruments(RemoveQuotationUseCase.Params params) {
        dbDataStore.removeInstruments(params.getNames());
        return cloudDataStore.removeSubscription(params.getNames());
    }

    @Override
    public Observable<List<String>> getInstruments() {
        return dbDataStore.getInstruments();
    }
}
