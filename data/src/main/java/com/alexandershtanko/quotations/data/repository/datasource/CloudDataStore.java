package com.alexandershtanko.quotations.data.repository.datasource;

import com.alexandershtanko.quotations.data.models.QuotationsListEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 06/09/2017.
 *
 */

public interface CloudDataStore {
    Observable<QuotationsListEntity> getQuotations();

    Observable<Boolean> subscribe(List<String> instruments);

    Observable<Boolean> unsubscribe(List<String> instruments);

    Observable<Boolean> getConnectionState();
}
