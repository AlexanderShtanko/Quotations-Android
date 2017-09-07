package com.alexandershtanko.quotations.data.cache;

import android.content.Context;

import com.alexandershtanko.quotations.data.utils.paper.RxPaper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 07/09/2017.
 *         Copyright Ostrovok.ru
 */
@Module
public class CacheModule {
    @Provides
    @Singleton
    RxPaper provideRxPaper(Context context) {
        RxPaper rxPaper = new RxPaper();
        rxPaper.init(context);
        return rxPaper;
    }

}