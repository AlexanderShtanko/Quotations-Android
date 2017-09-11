package com.alexandershtanko.quotations.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 05/09/2017.
 *
 */
@Module
public class AppModule {
    Context appContext;
    public AppModule(Context appContext)
    {
        this.appContext=appContext;
    }

    @Provides
    @Singleton
    public Context getContext()
    {
        return appContext;
    }
}
