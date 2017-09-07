package com.alexandershtanko.quotations.data.net;

import android.content.Context;
import android.content.Intent;

import com.alexandershtanko.quotations.data.utils.RxBus;
import com.alexandershtanko.quotations.data.utils.RxService;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by aleksandr on 06.09.17.
 */

public class WebSocketService extends RxService {
    private static final String URL = "ws://echo.websocket.org";

    private final static String ACTION_SUBSCRIBE = "subscribe_action";
    private final static String ACTION_UNSUBSCRIBE = "unsubscribe_action";
    private static final String EXTRA_ACTION = "action";
    private static final String EXTRA_INSTRUMENTS = "instruments";
    public static final String RX_BUS_ACTION_QUOTATIONS = "quotations";
    public static final String RX_BUS_ACTION_STATE = "state";
    RxWebSocket rxWebSocket = new RxWebSocket();
    @Inject
    RxBus rxBus;


    @Override
    protected void onSubscribe(CompositeDisposable s) {
        s.add(rxWebSocket.getOnMessageObservable().map().map().subscribe(this::setQuotations, ErrorUtils::log));
        s.add(rxWebSocket.getSocketConnectionStateObservable().subscribe(this::setSocketConnectionState, ErrorUtils::log));
    }

    private void setQuotations(List<Quotation> quotations) {
        rxBus.callAction(RX_BUS_ACTION_QUOTATIONS, quotations);
    }

    private void setSocketConnectionState(Boolean state) {
        rxBus.callAction(RX_BUS_ACTION_STATE, state);
    }


    public static void subscribe(Context context, List<String> instruments) {
        if (context != null && instruments != null) {
            Intent intent = new Intent(context, WebSocketService.class);
            intent.putExtra(EXTRA_ACTION, ACTION_SUBSCRIBE);
            intent.putExtra(EXTRA_INSTRUMENTS, instruments.toArray());
            context.startService(intent);
        }
    }

    public static void unsubscribe(Context context, List<String> instruments) {
        if (context != null && instruments != null) {
            Intent intent = new Intent(context, WebSocketService.class);
            intent.putExtra(EXTRA_ACTION, ACTION_UNSUBSCRIBE);
            intent.putExtra(EXTRA_INSTRUMENTS, instruments.toArray());
            context.startService(intent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.hasExtra(EXTRA_ACTION) && intent.hasExtra(EXTRA_INSTRUMENTS)) {
                String action = intent.getStringExtra(EXTRA_ACTION);
                String[] instruments = intent.getStringArrayExtra(EXTRA_INSTRUMENTS);
                switch (action) {
                    case ACTION_SUBSCRIBE:
                        subscribeToUpdates(instruments);
                        break;
                    case ACTION_UNSUBSCRIBE:
                        unsubscribeFromUpdates(instruments);
                        break;
                }
            }
        }
        return START_STICKY;
    }

    private void unsubscribeFromUpdates(String[] instruments) {

    }

    private void subscribeToUpdates(String[] instruments) {

    }
}
