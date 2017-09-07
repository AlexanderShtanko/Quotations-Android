package com.alexandershtanko.quotations.data.cache;

import com.alexandershtanko.quotations.data.repository.datasource.CacheDataStore;
import com.alexandershtanko.quotations.data.utils.paper.RxPaper;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 07/09/2017.
 *         Copyright Ostrovok.ru
 */

public class PaperCacheDataStore implements CacheDataStore {
    public static final String KEY_QUOTATIONS = "quotations";
    public static final String KEY_INSTRUMENTS = "instruments";
    private final RxPaper rxPaper;
    private final static String BOOK_MAIN = "main";

    @Inject
    public PaperCacheDataStore(RxPaper rxPaper) {
        this.rxPaper = rxPaper;
    }

    @Override
    public void setQuotations(List<Quotation> quotations) {
        rxPaper.write(BOOK_MAIN, KEY_QUOTATIONS, quotations);
    }

    @Override
    public List<Quotation> getQuotations() {
        RxPaper.PaperObject<List<Quotation>> obj = rxPaper.readOnce(BOOK_MAIN, KEY_QUOTATIONS);
        if (obj != null)
            return obj.getObject();
        return new ArrayList<>();
    }

    @Override
    public void addInstruments(List<String> names) {
        rxPaper.write(BOOK_MAIN, KEY_INSTRUMENTS, names);
    }

    @Override
    public void removeInstruments(List<String> names) {
        List<String> instruments = getInstrumentsOnce();
        for (String name : names) {
            instruments.remove(name);
        }

        rxPaper.write(BOOK_MAIN, KEY_INSTRUMENTS, instruments);
    }

    private List<String> getInstrumentsOnce() {
        List<String> instruments = new ArrayList<>();
        RxPaper.PaperObject<List<String>> obj = rxPaper.readOnce(BOOK_MAIN, KEY_INSTRUMENTS);
        if (obj != null)
            instruments = obj.getObject();
        return instruments;
    }

    @Override
    public Observable<List<String>> getInstruments() {
        Observable<RxPaper.PaperObject<List<String>>> observable = rxPaper.read(BOOK_MAIN, KEY_INSTRUMENTS);

        return observable.map(objectPaperObject -> {
            if (objectPaperObject != null && objectPaperObject.getObject() != null)
                return objectPaperObject.getObject();
            return new ArrayList<String>();
        });
    }
}
