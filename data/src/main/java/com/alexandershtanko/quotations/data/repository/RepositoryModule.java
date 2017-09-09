package com.alexandershtanko.quotations.data.repository;

import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aleksandr on 07.09.17.
 */
@Module
public class RepositoryModule {
    @Provides
    public Scheduler getScheduler() {
        return Schedulers.io();
    }

    @Provides
    public QuotationsRepository provideQuotationRepository(QuotationsDataRepository repository) {
        return repository;
    }
}
