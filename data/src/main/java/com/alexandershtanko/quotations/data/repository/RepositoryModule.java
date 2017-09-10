package com.alexandershtanko.quotations.data.repository;

import com.alexandershtanko.quotations.data.cache.CacheModule;
import com.alexandershtanko.quotations.data.cloud.CloudModule;
import com.alexandershtanko.quotations.data.repository.datasource.CacheDataStore;
import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;
import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aleksandr on 07.09.17.
 */
@Module(includes = {CloudModule.class, CacheModule.class})
public class RepositoryModule {
    @Provides
    public Scheduler getScheduler() {
        return Schedulers.io();
    }

    @Provides
    public QuotationsRepository provideQuotationRepository(CloudDataStore cloudDataStore, CacheDataStore cacheDataStore) {
        return new QuotationsDataRepository(cloudDataStore, cacheDataStore);
    }
}
