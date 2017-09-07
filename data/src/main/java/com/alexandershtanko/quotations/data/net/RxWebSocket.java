package com.alexandershtanko.quotations.data.net;

import com.alexandershtanko.quotations.data.utils.ErrorUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 07/09/2017.
 *         Copyright Ostrovok.ru
 */

public class RxWebSocket {

    private final OkHttpClient okHttpClient;
    private final Request request;
    private BehaviorSubject<WebSocket> webSocketSubject = BehaviorSubject.create();
    private PublishSubject<String> onMessageSubject = PublishSubject.create();
    private PublishSubject<OnFailureEvent> onFailureSubject = PublishSubject.create();

    private WebSocketListener webSocketListener = new RxWebSocketListener();

    @Inject
    public RxWebSocket(OkHttpClient okHttpClient, Request request) {
        this.okHttpClient = okHttpClient;
        this.request = request;
        webSocketSubject.onNext(null);
    }

    public Observable<Boolean> sendMessage(String message) {
        return onConnected().map(ws -> ws.send(message));
    }

    public Observable<String> getMessagesObservable() {
        return getOrCreateWebSocket().switchMap(ws -> onMessageSubject);
    }

    private Observable<WebSocket> getWebSocket() {
        return webSocketSubject;
    }

    private Observable<WebSocket> getOrCreateWebSocket() {
        return getWebSocket().map(ws -> {
            if (ws == null)
                return createWebSocket();
            else return ws;
        }).doOnDispose(this::closeWebSocket);
    }

    private Observable<WebSocket> onConnected() {
        return webSocketSubject.filter(ws -> ws != null);
    }

    private synchronized WebSocket createWebSocket() {
        try {
            if (webSocketSubject.getValue() == null) {
                return okHttpClient.newWebSocket(request, webSocketListener);
            }

        } catch (Exception ex) {
            webSocketSubject.onError(ex);
            ErrorUtils.log(ex);
        }
        return null;
    }

    private synchronized void closeWebSocket() {
        try {
            WebSocket ws = webSocketSubject.getValue();
            if (ws != null) {
                ws.cancel();
            }
        } catch (Exception e) {
            ErrorUtils.log(e);
        }

        if (webSocketSubject.getValue() != null)
            webSocketSubject.onNext(null);
    }

    public Observable<Boolean> getConnectionState() {
        return getWebSocket().map(ws -> ws != null);
    }


    private class RxWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocketSubject.onNext(webSocket);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            onMessageSubject.onNext(text);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocketSubject.onNext(null);

        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            onFailureSubject.onNext(new OnFailureEvent(t, response));
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
        }
    }

    public static class OnFailureEvent {
        private Throwable throwable;
        private Response response;

        public Throwable getThrowable() {
            return throwable;
        }

        public void setThrowable(Throwable throwable) {
            this.throwable = throwable;
        }

        public OnFailureEvent(Throwable throwable, Response response) {
            this.throwable = throwable;
            this.response = response;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }
}
