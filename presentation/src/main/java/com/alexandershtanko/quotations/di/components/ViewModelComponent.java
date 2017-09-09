package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.di.modules.ViewModelModule;
import com.alexandershtanko.quotations.domain.interactor.AddQuotationUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetConnectionStateUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;
import com.alexandershtanko.quotations.viewmodels.InstrumentsViewModel;
import com.alexandershtanko.quotations.viewmodels.QuotationsViewModel;

import dagger.Component;

/**
 * Created by aleksandr on 09.09.17.
 */
@Component(dependencies = {UseCaseComponent.class}, modules = {ViewModelModule.class})
public interface ViewModelComponent {
    InstrumentsViewModel provideInstrumentsViewModel(GetInstrumentsUseCase getInstrumentsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, AddQuotationUseCase addQuotationUseCase, RemoveQuotationUseCase removeQuotationUseCase);
    QuotationsViewModel provideQuotationsViewModel(GetQuotationsUseCase getQuotationsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, RemoveQuotationUseCase removeQuotationUseCase, GetConnectionStateUseCase getConnectionStateUseCase, AddQuotationUseCase addQuotationUseCase);
}
