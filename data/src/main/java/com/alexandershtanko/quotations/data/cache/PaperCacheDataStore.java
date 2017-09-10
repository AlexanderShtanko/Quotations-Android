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
    private final List<String> instruments;

    @Inject
    public PaperCacheDataStore(RxPaper rxPaper, List<String> instruments) {
        this.rxPaper = rxPaper;
        this.instruments = instruments;
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
    public synchronized void addInstruments(List<String> names) {
        List<String> list = getSelectedInstrumentsOnce();
        if (names != null) {
            for (String name : names) {
                if (!list.contains(name))
                    list.add(name);
            }
        }
        rxPaper.write(BOOK_MAIN, KEY_INSTRUMENTS, list);
    }

    @Override
    public synchronized void removeInstruments(List<String> names) {
        List<String> instruments = getSelectedInstrumentsOnce();
        if (names != null)
            for (String name : names) {
                if (instruments.contains(name))
                    instruments.remove(name);
            }

        rxPaper.write(BOOK_MAIN, KEY_INSTRUMENTS, instruments);
    }

    private List<String> getSelectedInstrumentsOnce() {
        List<String> instruments = new ArrayList<>();
        RxPaper.PaperObject<List<String>> obj = rxPaper.readOnce(BOOK_MAIN, KEY_INSTRUMENTS);
        if (obj != null)
            instruments = obj.getObject();
        return instruments;
    }

    @Override
    public Observable<List<String>> getSelectedInstruments() {
        if (!rxPaper.exist(BOOK_MAIN, KEY_INSTRUMENTS))
            rxPaper.write(BOOK_MAIN, KEY_INSTRUMENTS, instruments);

        Observable<RxPaper.PaperObject<List<String>>> observable = rxPaper.read(BOOK_MAIN, KEY_INSTRUMENTS);
        return observable.map(objectPaperObject -> {
            if (objectPaperObject != null && objectPaperObject.getObject() != null)
                return objectPaperObject.getObject();
            return instruments;
        });
    }

    @Override
    public Observable<List<String>> getInstruments() {
        return Observable.just(instruments);
    }
}
