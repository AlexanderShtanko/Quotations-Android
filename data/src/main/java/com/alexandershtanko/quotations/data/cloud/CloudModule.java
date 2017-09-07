package com.alexandershtanko.quotations.data.cloud;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static final String WEB_SOCKET_URL = "wss://quotes.exness.com:18400";

    @Provides
    OkHttpClient provideClient() {
        return new OkHttpClient();
    }

    @Provides
    Request provideRequest() {
        return new Request.Builder().url(WEB_SOCKET_URL).build();
    }

    @Provides
    ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

}
