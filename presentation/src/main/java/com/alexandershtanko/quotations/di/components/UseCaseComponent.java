package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.di.modules.UseCaseModule;
import com.alexandershtanko.quotations.di.scopes.UseCaseScope;
import com.alexandershtanko.quotations.domain.interactor.SubscribeUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetConnectionStateUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.UnsubscribeUseCase;

import dagger.Component;

/**
 * Created by aleksandr on 09.09.17.
 */
@Component(dependencies = {DataComponent.class}, modules = {UseCaseModule.class})
@UseCaseScope
public interface UseCaseComponent {
    SubscribeUseCase AddQuotationUseCase();
    GetConnectionStateUseCase GetConnectionStateUseCase();
    GetInstrumentsUseCase GetInstrumentsUseCase();
    GetQuotationsUseCase GetQuotationsUseCase();
    GetSelectedInstrumentsUseCase GetSelectedInstrumentsUseCase();
    UnsubscribeUseCase RemoveQuotationUseCase();

}
