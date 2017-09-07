package com.alexandershtanko.quotations.data.repository;

import com.alexandershtanko.quotations.data.mappers.QuotationEntityDataMapper;
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
import io.reactivex.ObservableOnSubscribe;

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
        return Observable.create((ObservableOnSubscribe<List<Quotation>>) e -> {
            List<Quotation> quotations = dbDataStore.getQuotations();
            if (quotations != null)
                e.onNext(quotations);
        }).mergeWith(cloudDataStore.getQuotations()
                .map(QuotationEntityDataMapper::transform)
                .doOnNext(dbDataStore::setQuotations));
    }

    @Override
    public Observable<Boolean> addInstruments(AddQuotationUseCase.Params params) {

        return cloudDataStore.subscribe(params.getNames()).doOnNext(res -> {
            if (res)
                dbDataStore.addInstruments(params.getNames());
        });
    }

    @Override
    public Observable<Boolean> removeInstruments(RemoveQuotationUseCase.Params params) {
        return cloudDataStore.unsubscribe(params.getNames()).doOnNext(res -> {
            if (res)
                dbDataStore.removeInstruments(params.getNames());
        });
    }

    @Override
    public Observable<List<String>> getInstruments() {
        return dbDataStore.getInstruments();
    }

    @Override
    public Observable<Boolean> getConnectionState() {
        return cloudDataStore.getConnectionState();
    }

}
