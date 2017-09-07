package com.alexandershtanko.quotations.data.repository.datasource;

import com.alexandershtanko.quotations.data.models.QuotationsResponseEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public interface CloudDataStore {
    Observable<QuotationsResponseEntity> getQuotations();

    Observable<Boolean> subscribe(List<String> instruments);

    Observable<Boolean> unsubscribe(List<String> instruments);

    Observable<Boolean> getConnectionState();
}
