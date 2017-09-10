package com.alexandershtanko.quotations.data.cache;

import android.content.Context;

import com.alexandershtanko.quotations.data.repository.DataScope;
import com.alexandershtanko.quotations.data.repository.datasource.CacheDataStore;
import com.alexandershtanko.quotations.data.utils.paper.RxPaper;

import java.util.ArrayList;
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
    List<String> instruments= new ArrayList<>(Arrays.asList("EURUSD", "EURGBP", "USDJPY", "GBPUSD", "USDCHF", "USDCAD", "AUDUSD", "EURJPY",
            "EURCHF"));

    @Provides
    @DataScope
    public RxPaper provideRxPaper(Context context) {
        RxPaper rxPaper = new RxPaper();
        rxPaper.init(context);
        return rxPaper;
    }

    @Provides
    @DataScope
    public CacheDataStore provideCacheDataStore(RxPaper paper)
    {
        return new PaperCacheDataStore(paper,instruments);
    }



}
