package com.alexandershtanko.quotations.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */
@Module
@Singleton
public class AppModule {
    Context appContext;
    public AppModule(Context appContext)
    {
        this.appContext=appContext;
    }

    @Provides
    public Context getContext()
    {
        return appContext;
    }
}
