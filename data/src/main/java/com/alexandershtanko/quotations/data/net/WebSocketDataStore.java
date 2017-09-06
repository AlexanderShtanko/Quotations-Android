package com.alexandershtanko.quotations.data.net;

import com.alexandershtanko.quotations.data.models.QuotationEntity;
import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public class WebSocketDataStore implements CloudDataStore {
    @Override
    public Observable<List<QuotationEntity>> getQuotations() {
        return null;
    }

    @Override
    public Observable<QuotationEntity> addQuotation(String quotationName) {
        return null;
    }

    @Override
    public Observable<QuotationEntity> removeQuotation(String quotationName) {
        return null;
    }
}
