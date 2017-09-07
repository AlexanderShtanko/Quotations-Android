package com.alexandershtanko.quotations.data.net;

import com.alexandershtanko.quotations.data.mappers.DataMapper;
import com.alexandershtanko.quotations.data.models.QuotationsResponseEntity;
import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 07/09/2017.
 *         Copyright Ostrovok.ru
 */

public class WebSocketCloudDataStore implements CloudDataStore {

    private final RxWebSocket rxWebSocket;
    private final DataMapper dataMapper;

    @Inject
    public WebSocketCloudDataStore(DataMapper dataMapper, RxWebSocket rxWebSocket) {
        this.rxWebSocket = rxWebSocket;
        this.dataMapper = dataMapper;
    }

    @Override
    public Observable<QuotationsResponseEntity> getQuotations() {
        return rxWebSocket.getMessagesObservable().map(dataMapper::getQuotations).filter(l -> l != null);
    }

    @Override
    public Observable<Boolean> subscribe(List<String> instruments) {
        assert instruments != null;
        String message = dataMapper.getSubscribeMessage(instruments);
        return rxWebSocket.sendMessage(message);
    }

    @Override
    public Observable<Boolean> unsubscribe(List<String> instruments) {
        assert instruments != null;
        String message = dataMapper.getUnsubscribeMessage(instruments);
        return rxWebSocket.sendMessage(message);
    }

    @Override
    public Observable<Boolean> getConnectionState() {
        return rxWebSocket.getConnectionState();
    }
}
