package com.alexandershtanko.quotations.data.net;

import com.alexandershtanko.quotations.data.utils.RxService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * Created by aleksandr on 06.09.17.
 */

public class WebSocketService extends RxService {
    BehaviorSubject<String> inputMessageSubject=BehaviorSubject.create();
    BehaviorSubject<String> outputMessageSubject=BehaviorSubject.create();

    @Override
    protected void onSubscribe(CompositeDisposable s) {
        s.add();
    }

    private void start() {
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        WebSocketDataListener listener = new WebSocketDataListener();
        OkHttpClient client = new OkHttpClient();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }
}
