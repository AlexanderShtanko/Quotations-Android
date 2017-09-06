package com.alexandershtanko.quotations.data.repository.datasource;

import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public interface CacheDataStore {
    void setQuotations(List<Quotation> quotations);
    Observable<List<Quotation>> getQuotations();
}
