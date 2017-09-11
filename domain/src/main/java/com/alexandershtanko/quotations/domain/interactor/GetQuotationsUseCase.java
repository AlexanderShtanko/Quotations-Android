package com.alexandershtanko.quotations.domain.interactor;

import com.alexandershtanko.quotations.domain.models.Quotation;
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

public class GetQuotationsUseCase extends UseCase<List<Quotation>, Void> {
    private final QuotationsRepository repository;

    @Inject
    public GetQuotationsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        super(subscriptionScheduler);
        this.repository = repository;
    }

    public Observable<List<Quotation>> execute() {
        return execute(null);
    }

    @Override
    Observable<List<Quotation>> buildUseCaseObservable(Void aVoid) {
        return repository.getQuotations();
    }

}
