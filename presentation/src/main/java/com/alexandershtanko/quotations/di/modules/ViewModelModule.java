package com.alexandershtanko.quotations.di.modules;

import com.alexandershtanko.quotations.domain.interactor.AddQuotationUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetConnectionStateUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;
import com.alexandershtanko.quotations.viewmodels.InstrumentsViewModel;
import com.alexandershtanko.quotations.viewmodels.QuotationsViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aleksandr on 09.09.17.
 */
@Module
public class ViewModelModule {
    @Provides
    InstrumentsViewModel provideInstrumentsViewModel(GetInstrumentsUseCase getInstrumentsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, AddQuotationUseCase addQuotationUseCase, RemoveQuotationUseCase removeQuotationUseCase) {
        return new InstrumentsViewModel(getInstrumentsUseCase, getSelectedInstrumentsUseCase, addQuotationUseCase, removeQuotationUseCase);
    }

    @Provides
    QuotationsViewModel provideQuotationsViewModel(GetQuotationsUseCase getQuotationsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, RemoveQuotationUseCase removeQuotationUseCase, GetConnectionStateUseCase getConnectionStateUseCase, AddQuotationUseCase addQuotationUseCase) {
        return new QuotationsViewModel(getQuotationsUseCase, getSelectedInstrumentsUseCase, removeQuotationUseCase, getConnectionStateUseCase, addQuotationUseCase);
    }

}
