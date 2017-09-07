package com.alexandershtanko.quotations.data.repository;

import com.alexandershtanko.quotations.data.cache.PaperCacheDataStore;
import com.alexandershtanko.quotations.data.cloud.WebSocketCloudDataStore;
import com.alexandershtanko.quotations.data.repository.datasource.CacheDataStore;
import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aleksandr on 07.09.17.
 */
@Module
public class RepositoryModule {
    @Provides
    @Singleton
    public CloudDataStore provideCloudDataStore(WebSocketCloudDataStore dataStore)
    {
        return dataStore;
    }

    @Provides
    @Singleton
    public CacheDataStore provideCloudDataStore(PaperCacheDataStore dataStore)
    {
        return dataStore;
    }
}
