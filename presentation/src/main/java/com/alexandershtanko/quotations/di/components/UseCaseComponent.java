package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.data.repository.RepositoryModule;
import com.alexandershtanko.quotations.di.modules.UseCaseModule;
import com.alexandershtanko.quotations.domain.interactor.AddQuotationUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetConnectionStateUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;
import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import dagger.Component;
import io.reactivex.Scheduler;

/**
 * Created by aleksandr on 09.09.17.
 */
@Component(dependencies = {DataComponent.class}, modules = {RepositoryModule.class, UseCaseModule.class})
public interface UseCaseComponent {
    AddQuotationUseCase AddQuotationUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler);
    GetConnectionStateUseCase GetConnectionStateUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler);
    GetInstrumentsUseCase GetInstrumentsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler);
    GetQuotationsUseCase GetQuotationsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler);
    GetSelectedInstrumentsUseCase GetSelectedInstrumentsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler);
    RemoveQuotationUseCase RemoveQuotationUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler);

}
