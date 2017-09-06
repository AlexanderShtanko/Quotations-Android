package com.alexandershtanko.quotations.domain.interactor;

import com.alexandershtanko.quotations.domain.models.Quotation;
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

public class GetQuotationsUseCase extends UseCase<List<Quotation>, GetQuotationsUseCase.Params> {
    private final QuotationsRepository repository;

    @Inject
    public GetQuotationsUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        super(subscriptionScheduler);
        this.repository=repository;
    }

    @Override
    Observable<List<Quotation>> buildUseCaseObservable(Params params) {
        return repository.getQuotations(params);
    }

    public static class Params {
        private List<String> quotationNames;

        public List<String> getQuotationNames() {
            return quotationNames;
        }

        public void setQuotationNames(List<String> quotationNames) {
            this.quotationNames = quotationNames;
        }

        public Params(List<String> quotationNames) {
            this.quotationNames = quotationNames;
        }
    }
}
