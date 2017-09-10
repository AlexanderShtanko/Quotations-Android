package com.alexandershtanko.quotations;

import android.app.Application;

import com.alexandershtanko.quotations.di.components.AppComponent;
import com.alexandershtanko.quotations.di.components.DaggerAppComponent;
import com.alexandershtanko.quotations.di.components.DaggerFragmentsComponent;
import com.alexandershtanko.quotations.di.components.FragmentsComponent;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static FragmentsComponent fragmentsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
        fragmentsComponent = buildFragmentsComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .build();
    }

    private FragmentsComponent buildFragmentsComponent() {
        return DaggerFragmentsComponent.builder()
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

    public static FragmentsComponent getFragmentsComponent() {
        return fragmentsComponent;
    }
}
