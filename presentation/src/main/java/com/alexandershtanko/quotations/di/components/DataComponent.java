package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.data.cache.CacheModule;
import com.alexandershtanko.quotations.data.cache.PaperCacheDataStore;
import com.alexandershtanko.quotations.data.cloud.CloudModule;
import com.alexandershtanko.quotations.data.cloud.RxWebSocket;
import com.alexandershtanko.quotations.data.cloud.WebSocketCloudDataStore;
import com.alexandershtanko.quotations.data.mappers.DataMapper;
import com.alexandershtanko.quotations.data.repository.QuotationsDataRepository;
import com.alexandershtanko.quotations.data.repository.datasource.CacheDataStore;
import com.alexandershtanko.quotations.data.repository.datasource.CloudDataStore;
import com.alexandershtanko.quotations.data.utils.paper.RxPaper;

import java.util.List;

import dagger.Component;

/**
 * Created by aleksandr on 09.09.17.
 */
@Component(modules = {CloudModule.class, CacheModule.class})
public interface DataComponent {
    WebSocketCloudDataStore WebSocketCloudDataStore(DataMapper dataMapper, RxWebSocket rxWebSocket);

    PaperCacheDataStore PaperCacheDataStore(RxPaper rxPaper, List<String> instruments);

    QuotationsDataRepository QuotationsDataRepository(CloudDataStore cloudDataStore, CacheDataStore cacheDataStore);
}
