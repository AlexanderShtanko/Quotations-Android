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
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 06/09/2017.
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
            if (quotations != null) {
                List<String> sortOrder = cacheDataStore.getSort();
                if (sortOrder != null)
                    quotations.sort((q1, q2) -> {
                        Integer ind1=sortOrder.indexOf(q1.getSymbol());
                        Integer ind2=sortOrder.indexOf(q2.getSymbol());
                        return ind1.compareTo(ind2);
                    });


                e.onNext(quotations);
            }
        }).mergeWith(cloudDataStore.getQuotations()
                .map(QuotationEntityDataMapper::transform)
                .map(cacheDataStore::addQuotations));
    }

    @Override
    public Observable<Boolean> addInstruments(SubscribeUseCase.Params params) {
        cacheDataStore.addInstruments(params.getNames());
        return cloudDataStore.subscribe(params.getNames()).toFlowable(BackpressureStrategy.BUFFER).toObservable();
    }

    @Override
    public Observable<Boolean> removeInstruments(UnsubscribeUseCase.Params params) {
        cacheDataStore.removeInstruments(params.getNames());
        return cloudDataStore.unsubscribe(params.getNames()).doOnNext(res -> {
        }).toFlowable(BackpressureStrategy.BUFFER).toObservable();
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
    public Observable<Boolean> updateSort(List<String> keys) {
        cacheDataStore.updateSort(keys);
        return Observable.just(true);
    }


    @Override
    public Observable<Boolean> getConnectionState() {
        return cloudDataStore.getConnectionState();
    }


}
