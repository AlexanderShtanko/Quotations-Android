package com.alexandershtanko.quotations.data.cache;

import com.alexandershtanko.quotations.data.repository.datasource.DbDataStore;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 07/09/2017.
 *         Copyright Ostrovok.ru
 */

public class PaperDbDataStore implements DbDataStore {
    @Override
    public void setQuotations(List<Quotation> quotations) {

    }

    @Override
    public List<Quotation> getQuotations() {
        return null;
    }

    @Override
    public void addInstruments(List<String> names) {

    }

    @Override
    public void removeInstruments(List<String> names) {

    }

    @Override
    public Observable<List<String>> getInstruments() {
        return null;
    }
}
