package com.alexandershtanko.quotations.di.modules;

import com.alexandershtanko.quotations.di.scopes.ViewModelScope;
import com.alexandershtanko.quotations.domain.interactor.SubscribeUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetConnectionStateUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetSelectedInstrumentsUseCase;
import com.alexandershtanko.quotations.domain.interactor.UnsubscribeUseCase;
import com.alexandershtanko.quotations.viewmodels.InstrumentsViewModel;
import com.alexandershtanko.quotations.viewmodels.MainActivityViewModel;
import com.alexandershtanko.quotations.viewmodels.QuotationsViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aleksandr on 09.09.17.
 */
@Module
public class ViewModelModule {

    @ViewModelScope
    @Provides
    InstrumentsViewModel provideInstrumentsViewModel(GetInstrumentsUseCase getInstrumentsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, SubscribeUseCase subscribeUseCase, UnsubscribeUseCase unsubscribeUseCase) {
        return new InstrumentsViewModel(getInstrumentsUseCase, getSelectedInstrumentsUseCase, subscribeUseCase, unsubscribeUseCase);
    }

    @ViewModelScope
    @Provides
    QuotationsViewModel provideQuotationsViewModel(GetQuotationsUseCase getQuotationsUseCase, GetSelectedInstrumentsUseCase getSelectedInstrumentsUseCase, UnsubscribeUseCase unsubscribeUseCase, GetConnectionStateUseCase getConnectionStateUseCase, SubscribeUseCase subscribeUseCase) {
        return new QuotationsViewModel(getQuotationsUseCase, getSelectedInstrumentsUseCase, unsubscribeUseCase, getConnectionStateUseCase, subscribeUseCase);
    }

    @ViewModelScope
    @Provides
    MainActivityViewModel provideMainActivityViewModel()
    {
        return new MainActivityViewModel();
    }

}
