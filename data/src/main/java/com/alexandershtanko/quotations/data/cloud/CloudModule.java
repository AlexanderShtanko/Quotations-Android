package com.alexandershtanko.quotations.data.cloud;

import com.alexandershtanko.quotations.data.mappers.DataMapper;
import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;
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
    public OkHttpClient provideClient() {
        return new OkHttpClient();
    }

    @Provides
    public Request provideRequest() {
        return new Request.Builder().url(WEB_SOCKET_URL).build();
    }

    @Provides
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides
    public DataMapper getDataMapper(ObjectMapper objectMapper) {
        return new DataMapper(objectMapper);
    }

    @Provides
    public RxWebSocket provideRxWebSocket(OkHttpClient client, Request request) {

        return new RxWebSocket(client, request);
    }

    @Provides
    public CloudDataStore provideCloudDataStore(DataMapper dataMapper, RxWebSocket rxWebSocket) {
        return new WebSocketCloudDataStore(dataMapper, rxWebSocket);
    }

}
