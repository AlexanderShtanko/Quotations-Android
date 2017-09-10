package com.alexandershtanko.quotations;

import android.app.Application;

import com.alexandershtanko.quotations.data.cache.CacheModule;
import com.alexandershtanko.quotations.data.cloud.CloudModule;
import com.alexandershtanko.quotations.data.repository.RepositoryModule;
import com.alexandershtanko.quotations.di.components.ActivitiesComponent;
import com.alexandershtanko.quotations.di.components.AppComponent;
import com.alexandershtanko.quotations.di.components.DaggerActivitiesComponent;
import com.alexandershtanko.quotations.di.components.DaggerAppComponent;
import com.alexandershtanko.quotations.di.components.DaggerDataComponent;
import com.alexandershtanko.quotations.di.components.DaggerFragmentsComponent;
import com.alexandershtanko.quotations.di.components.DaggerUseCaseComponent;
import com.alexandershtanko.quotations.di.components.DaggerViewModelComponent;
import com.alexandershtanko.quotations.di.components.DataComponent;
import com.alexandershtanko.quotations.di.components.FragmentsComponent;
import com.alexandershtanko.quotations.di.components.UseCaseComponent;
import com.alexandershtanko.quotations.di.components.ViewModelComponent;
import com.alexandershtanko.quotations.di.modules.AppModule;
import com.alexandershtanko.quotations.di.modules.UseCaseModule;
import com.alexandershtanko.quotations.di.modules.ViewModelModule;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static FragmentsComponent fragmentsComponent;
    private static UseCaseComponent useCaseComponent;
    private static DataComponent dataComponent;
    private static ViewModelComponent viewModelComponent;
    private static ActivitiesComponent activitiesComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
        dataComponent = buildDataComponent(appComponent);
        useCaseComponent = buildUseCaseComponent(dataComponent);
        viewModelComponent = buildViewModelComponent(useCaseComponent);
        fragmentsComponent = buildFragmentsComponent(viewModelComponent);
        activitiesComponent = buildActivitiesComponent(viewModelComponent);

    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(getBaseContext()))
                .build();
    }

    private DataComponent buildDataComponent(AppComponent appComponent) {
        return DaggerDataComponent.builder()
                .appComponent(appComponent)
                .cacheModule(new CacheModule())
                .cloudModule(new CloudModule())
                .repositoryModule(new RepositoryModule())
                .build();
    }

    private ViewModelComponent buildViewModelComponent(UseCaseComponent useCaseComponent) {
        return DaggerViewModelComponent.builder()
                .useCaseComponent(useCaseComponent)
                .viewModelModule(new ViewModelModule())
                .build();
    }
    private UseCaseComponent buildUseCaseComponent(DataComponent dataComponent) {
        return DaggerUseCaseComponent.builder()
                .dataComponent(dataComponent)
                .useCaseModule(new UseCaseModule())
                .build();
    }

    private FragmentsComponent buildFragmentsComponent(ViewModelComponent viewModelComponent) {
        return DaggerFragmentsComponent.builder()
                .viewModelComponent(viewModelComponent)
                .build();
    }

    private ActivitiesComponent buildActivitiesComponent(ViewModelComponent viewModelComponent) {
        return DaggerActivitiesComponent.builder()
                .viewModelComponent(viewModelComponent)
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

    public static ActivitiesComponent getActivitiesComponent() {
        return activitiesComponent;
    }
}
