package com.alexandershtanko.quotations.domain.interactor;

import com.alexandershtanko.quotations.domain.repository.QuotationsRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */

public class RemoveQuotationUseCase extends UseCase<RemoveQuotationUseCase.Result, RemoveQuotationUseCase.Params> {
    private final QuotationsRepository repository;

    @Inject
    public RemoveQuotationUseCase(QuotationsRepository repository, Scheduler subscriptionScheduler) {
        super(subscriptionScheduler);
        this.repository = repository;
    }

    @Override
    Observable<Result> buildUseCaseObservable(Params params) {
        return repository.removeQuotation(params);
    }

    public static class Params {
        private String quotationName;

        public String getQuotationName() {
            return quotationName;
        }

        public void setQuotationName(String quotationName) {
            this.quotationName = quotationName;
        }

        public Params(String quotationName) {
            this.quotationName = quotationName;
        }
    }

    public static class Result {
        private Boolean status;
        private String error;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
