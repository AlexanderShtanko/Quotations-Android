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

public class SubscribeUseCase extends UseCase<Boolean, SubscribeUseCase.Params> {
    private final QuotationsRepository repository;

    @Inject
    public SubscribeUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        super(subscriptionScheduler);
        this.repository = repository;
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(Params params) {
        return repository.addInstruments(params);
    }

    public static class Params {
        private List<String> quotations;

        public List<String> getNames() {
            return quotations;
        }

        public void setQuotations(List<String> quotations) {
            this.quotations = quotations;
        }

        public Params(List<String> quotations) {
            this.quotations = quotations;
        }
    }
}
