package com.alexandershtanko.quotations.data.repository;

import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;
import com.alexandershtanko.quotations.data.repository.datasource.CacheDataStore;
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
    private final CacheDataStore cacheDataStore;

    @Inject
    public QuotationsDataRepository(CloudDataStore cloudDataStore, CacheDataStore cacheDataStore) {
        this.cloudDataStore = cloudDataStore;
        this.cacheDataStore = cacheDataStore;
    }

    @Override
    public Observable<List<Quotation>> getQuotations(GetQuotationsUseCase.Params params) {
        return cacheDataStore.getQuotations();
    }

    @Override
    public Observable<AddQuotationUseCase.Result> addQuotation(AddQuotationUseCase.Params params) {
        return cloudDataStore.addQuotation(params.getQuotationName());
    }

    @Override
    public Observable<RemoveQuotationUseCase.Result> removeQuotation(RemoveQuotationUseCase.Params params) {
        return cloudDataStore.removeQuotation(params.getQuotationName());
    }
}
