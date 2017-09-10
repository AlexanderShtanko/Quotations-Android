package com.alexandershtanko.quotations.data.cloud;

import com.alexandershtanko.quotations.data.mappers.DataMapper;
import com.alexandershtanko.quotations.data.repository.DataScope;
import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

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
    @DataScope
    public OkHttpClient provideClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(0,TimeUnit.NANOSECONDS)
                .build();
        return okHttpClient;
    }

    @Provides
    @DataScope
    public Request provideRequest() {
        return new Request.Builder().url(WEB_SOCKET_URL).build();
    }

    @Provides
    @DataScope
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides
    @DataScope
    public DataMapper getDataMapper(ObjectMapper objectMapper) {
        return new DataMapper(objectMapper);
    }

    @Provides
    @DataScope
    public RxWebSocket provideRxWebSocket(OkHttpClient client, Request request) {

        return new RxWebSocket(client, request);
    }

    @Provides
    @DataScope
    public CloudDataStore provideCloudDataStore(DataMapper dataMapper, RxWebSocket rxWebSocket) {
        return new WebSocketCloudDataStore(dataMapper, rxWebSocket);
    }

}
