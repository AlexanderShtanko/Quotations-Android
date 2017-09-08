package com.alexandershtanko.quotations.domain.interactor;

import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */

public class GetInstrumentsUseCase extends UseCase<List<String>,Object> {
    private final QuotationsRepository repository;

    @Inject
    public GetInstrumentsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        super(subscriptionScheduler);
        this.repository = repository;
    }

    public Observable<List<String>> execute() {
        return execute(null);
    }

    @Override
    Observable<List<String>> buildUseCaseObservable(Object aVoid) {
        return repository.getInstruments();
    }
}
