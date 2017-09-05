package com.alexandershtanko.quotations;

import android.app.Application;

import com.alexandershtanko.quotations.di.components.AppComponent;
import com.alexandershtanko.quotations.di.components.DaggerAppComponent;
import com.alexandershtanko.quotations.di.modules.AppModule;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */

public class App extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }
}
