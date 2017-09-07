package com.alexandershtanko.quotations.data.net;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 07/09/2017.
 *         Copyright Ostrovok.ru
 */
@Module
public class CloudModule {
    @Provides
    OkHttpClient provideClient()
    {
        return new OkHttpClient();
    }

    @Provides
    Request provideRequest()
    {
        return okHttpClient;
    }
}
