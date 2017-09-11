package com.alexandershtanko.quotations.domain.interactor;

import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 05/09/2017.
 *
 */

public class GetConnectionStateUseCase extends UseCase<Boolean, Void> {
    private final QuotationsRepository repository;

    @Inject
    public GetConnectionStateUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        super(subscriptionScheduler);
        this.repository = repository;
    }

    public Observable<Boolean> execute() {
        return execute(null);
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(Void aVoid) {
        return repository.getConnectionState();
    }
}
