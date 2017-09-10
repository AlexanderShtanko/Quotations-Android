package com.alexandershtanko.quotations.data.cache;

import android.content.Context;

import com.alexandershtanko.quotations.data.repository.datasource.CacheDataStore;
import com.alexandershtanko.quotations.data.utils.paper.RxPaper;

import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 07/09/2017.
 *         Copyright Ostrovok.ru
 */
@Module
public class CacheModule {
    List<String> instruments= Arrays.asList("USD");

    @Provides
    public RxPaper provideRxPaper(Context context) {
        RxPaper rxPaper = new RxPaper();
        rxPaper.init(context);
        return rxPaper;
    }

    @Provides
    public CacheDataStore provideCacheDataStore(RxPaper paper)
    {
        return new PaperCacheDataStore(paper,instruments);
    }



}
