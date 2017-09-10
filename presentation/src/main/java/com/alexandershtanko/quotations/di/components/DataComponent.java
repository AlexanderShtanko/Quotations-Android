package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.data.repository.RepositoryModule;
import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import dagger.Component;
import io.reactivex.Scheduler;

/**
 * Created by aleksandr on 09.09.17.
 */
@Component(modules = {RepositoryModule.class}, dependencies = {AppComponent.class})
public interface DataComponent {

    QuotationsRepository QuotationsRepository();
    Scheduler scheduler();

}
