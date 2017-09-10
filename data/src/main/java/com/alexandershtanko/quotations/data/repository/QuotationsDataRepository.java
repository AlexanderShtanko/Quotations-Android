package com.alexandershtanko.quotations.data.repository;

import com.alexandershtanko.quotations.data.mappers.QuotationEntityDataMapper;
import com.alexandershtanko.quotations.data.repository.datasource.CacheDataStore;
import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;
import com.alexandershtanko.quotations.domain.interactor.SubscribeUseCase;
import com.alexandershtanko.quotations.domain.interactor.UnsubscribeUseCase;
import com.alexandershtanko.quotations.domain.models.Quotation;
import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationsDataRepository implements QuotationsRepository {

    private final CloudDataStore cloudDataStore;
    private final CacheDataStore cacheDataStore;

    @Inject
    public QuotationsDataRepository(CloudDataStore cloudDataStore, CacheDataStore cacheDataStore) {
        this.cloudDataStore = cloudDataStore;
        this.cacheDataStore = cacheDataStore;
    }

    @Override
    public Observable<List<Quotation>> getQuotations() {
        return Observable.create((ObservableOnSubscribe<List<Quotation>>) e -> {
            List<Quotation> quotations = cacheDataStore.getQuotations();
            if (quotations != null)
                e.onNext(quotations);
        }).mergeWith(cloudDataStore.getQuotations()
                .map(QuotationEntityDataMapper::transform)
                .doOnNext(cacheDataStore::setQuotations));
    }

    @Override
    public Observable<Boolean> addInstruments(SubscribeUseCase.Params params) {

        return cloudDataStore.subscribe(params.getNames()).doOnNext(res -> {
            if (res)
                cacheDataStore.addInstruments(params.getNames());
        }).toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    @Override
    public Observable<Boolean> removeInstruments(UnsubscribeUseCase.Params params) {
        return cloudDataStore.unsubscribe(params.getNames()).doOnNext(res -> {
            if (res)
                cacheDataStore.removeInstruments(params.getNames());
        }).toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    @Override
    public Observable<List<String>> getSelectedInstruments() {
        return cacheDataStore.getSelectedInstruments();
    }

    @Override
    public Observable<List<String>> getInstruments() {
        return cacheDataStore.getInstruments();
    }


    @Override
    public Observable<Boolean> getConnectionState() {
        return cloudDataStore.getConnectionState();
    }


}
