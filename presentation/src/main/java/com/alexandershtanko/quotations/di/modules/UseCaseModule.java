package com.alexandershtanko.quotations.di.modules;

import com.alexandershtanko.quotations.domain.interactor.AddQuotationUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetConnectionStateUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;
import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by aleksandr on 09.09.17.
 */
@Module
public class UseCaseModule {
    @Provides
    AddQuotationUseCase provideAddQuotationUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        return new AddQuotationUseCase(repository, subscriptionScheduler);
    }

    @Provides
    GetConnectionStateUseCase provideGetConnectionStateUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        return new GetConnectionStateUseCase(repository, subscriptionScheduler);
    }

    @Provides
    GetInstrumentsUseCase provideGetInstrumentsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        return new GetInstrumentsUseCase(repository, subscriptionScheduler);
    }

    @Provides
    GetQuotationsUseCase provideGetQuotationsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        return new GetQuotationsUseCase(repository, subscriptionScheduler);
    }

    @Provides
    GetSelectedInstrumentsUseCase GetSelectedInstrumentsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        return new GetSelectedInstrumentsUseCase(repository, subscriptionScheduler);
    }

    @Provides
    RemoveQuotationUseCase RemoveQuotationUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        return new RemoveQuotationUseCase(repository, subscriptionScheduler);
    }

}
