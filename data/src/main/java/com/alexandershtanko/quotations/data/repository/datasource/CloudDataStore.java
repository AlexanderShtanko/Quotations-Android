package com.alexandershtanko.quotations.data.repository.datasource;

import com.alexandershtanko.quotations.data.models.QuotationEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public interface CloudDataStore {
    Observable<List<QuotationEntity>> getQuotations();
    Observable<QuotationEntity> addQuotation(String quotationName);
    Observable<QuotationEntity> removeQuotation(String quotationName);
}
