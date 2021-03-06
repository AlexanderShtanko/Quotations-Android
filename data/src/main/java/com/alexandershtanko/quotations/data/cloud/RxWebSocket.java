package com.alexandershtanko.quotations.data.cloud;

import android.util.Log;

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
import okio.ByteString;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 07/09/2017.
 *
 */

public class RxWebSocket {

    private static final String TAG = "RxWebSocketLog";
    private final OkHttpClient okHttpClient;
    private final Request request;
    private WebSocket EMPTY_WEB_SOCKET = new WebSocket() {
        @Override
        public Request request() {
            return null;
        }

        @Override
        public long queueSize() {
            return 0;
        }

        @Override
        public boolean send(String text) {
            return false;
        }

        @Override
        public boolean send(ByteString bytes) {
            return false;
        }

        @Override
        public boolean close(int code, String reason) {
            return false;
        }

        @Override
        public void cancel() {

        }
    };

    private BehaviorSubject<WebSocket> webSocketSubject = BehaviorSubject.create();
    private PublishSubject<String> onMessageSubject = PublishSubject.create();
    private PublishSubject<OnFailureEvent> onFailureSubject = PublishSubject.create();

    private WebSocketListener webSocketListener = new RxWebSocketListener();
    private volatile boolean flgDoNotReconnect;

    @Inject
    public RxWebSocket(OkHttpClient okHttpClient, Request request) {
        this.okHttpClient = okHttpClient;
        this.request = request;
        webSocketSubject.onNext(EMPTY_WEB_SOCKET);
    }

    public Observable<Boolean> sendMessage(String message) {
        return onConnected().firstElement().toObservable().map(ws -> send(message, ws));
    }

    private boolean send(String message, WebSocket ws) {
        Log.e(TAG, "send:" + message);
        return ws.send(message);
    }

    public Observable<String> getMessagesObservable() {
        return getOrCreateWebSocket().switchMap(ws -> onMessageSubject.hide());
    }

    private Observable<WebSocket> getWebSocket() {
        return webSocketSubject.hide();
    }

    private Observable<WebSocket> getOrCreateWebSocket() {
        return getWebSocket().map(ws -> {
            if (ws == EMPTY_WEB_SOCKET && !flgDoNotReconnect)
                return createWebSocket();
            else return ws;
        }).doOnSubscribe(disposable -> flgDoNotReconnect = false)
                .doOnDispose(() -> {
                    flgDoNotReconnect = true;
                    this.closeWebSocket();
                });
    }

    private Observable<WebSocket> onConnected() {
        return webSocketSubject.hide().filter(ws -> ws != EMPTY_WEB_SOCKET);
    }

    private synchronized WebSocket createWebSocket() {
        try {
            if (webSocketSubject.getValue() == EMPTY_WEB_SOCKET) {
                Log.e(TAG, "Create websocket");

                return okHttpClient.newWebSocket(request, webSocketListener);
            }

        } catch (Exception ex) {
            webSocketSubject.onError(ex);
            ErrorUtils.log(ex);
        }
        return EMPTY_WEB_SOCKET;
    }

    private synchronized void closeWebSocket() {
        try {
            WebSocket ws = webSocketSubject.getValue();
            if (ws != EMPTY_WEB_SOCKET) {
                ws.cancel();
            }
        } catch (Exception e) {
            ErrorUtils.log(e);
        }

        if (webSocketSubject.getValue() != EMPTY_WEB_SOCKET)
            webSocketSubject.onNext(EMPTY_WEB_SOCKET);
    }

    public Observable<Boolean> getConnectionState() {
        return getWebSocket().map(ws -> ws != EMPTY_WEB_SOCKET);
    }


    private class RxWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.e(TAG, "On Open");

            webSocketSubject.onNext(webSocket);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.e(TAG, "received: " + text);

            onMessageSubject.onNext(text);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            Log.e(TAG, "On Closing");

            webSocketSubject.onNext(EMPTY_WEB_SOCKET);

        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.e(TAG, "On Failure");

            onFailureSubject.onNext(new OnFailureEvent(t, response));
            tryToReconnect();
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            Log.e(TAG, "On Closed");

        }
    }

    private void tryToReconnect() {
        closeWebSocket();
        if (!flgDoNotReconnect)
            webSocketSubject.onNext(createWebSocket());
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
