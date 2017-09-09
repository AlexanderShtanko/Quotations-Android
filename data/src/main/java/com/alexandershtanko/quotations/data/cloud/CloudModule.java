package com.alexandershtanko.quotations.data.cloud;

import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 07/09/2017.
 *         Copyright Ostrovok.ru
 */
@Singleton
@Module
public class CloudModule {

    public static final String WEB_SOCKET_URL = "wss://quotes.exness.com:18400";


    @Provides
    @Singleton
    public CloudDataStore provideCloudDataStore(WebSocketCloudDataStore dataStore) {
        return dataStore;
    }

    @Singleton
    @Provides
    OkHttpClient provideClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    Request provideRequest() {
        return new Request.Builder().url(WEB_SOCKET_URL).build();
    }

    @Singleton
    @Provides
    ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

}
