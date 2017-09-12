package com.alexandershtanko.quotations.domain.interactor;

import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 05/09/2017.
 *
 */

public class UpdateSortUseCase extends UseCase<Boolean,List<String>> {
    private final QuotationsRepository repository;

    @Inject
    public UpdateSortUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        super(subscriptionScheduler);
        this.repository = repository;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(List<String> keys) {
        return repository.updateSort(keys);
    }

}
