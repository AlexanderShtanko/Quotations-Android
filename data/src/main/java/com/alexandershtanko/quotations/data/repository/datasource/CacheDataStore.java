package com.alexandershtanko.quotations.data.repository.datasource;

import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 06/09/2017.
 *
 */

public interface CacheDataStore {
    void setQuotations(List<Quotation> quotations);
    List<Quotation> getQuotations();

    void addInstruments(List<String> names);
    void removeInstruments(List<String> names);
    Observable<List<String>> getSelectedInstruments();
    Observable<List<String>> getInstruments();

    List<Quotation> addQuotations(List<Quotation> quotations);
}
