package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.di.modules.ViewModelModule;
import com.alexandershtanko.quotations.di.scopes.ViewModelScope;
import com.alexandershtanko.quotations.viewmodels.InstrumentsViewModel;
import com.alexandershtanko.quotations.viewmodels.MainActivityViewModel;
import com.alexandershtanko.quotations.viewmodels.QuotationsViewModel;

import dagger.Component;

/**
 * Created by aleksandr on 09.09.17.
 */
@Component(dependencies = {UseCaseComponent.class}, modules = {ViewModelModule.class})
@ViewModelScope
public interface ViewModelComponent {
    InstrumentsViewModel provideInstrumentsViewModel();
    QuotationsViewModel provideQuotationsViewModel();
    MainActivityViewModel provideMainActivityViewModel();
}
