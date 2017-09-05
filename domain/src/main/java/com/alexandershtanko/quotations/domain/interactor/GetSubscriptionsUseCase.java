package com.alexandershtanko.quotations.domain.interactor;

import com.alexandershtanko.quotations.domain.models.Quotation;
import com.alexandershtanko.quotations.domain.repository.SubscriptionsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */

public class GetSubscriptionsUseCase extends UseCase<List<Quotation>, GetSubscriptionsUseCase.Params> {

    private final SubscriptionsRepository repository;

    @Inject
    GetSubscriptionsUseCase(SubscriptionsRepository repository, Scheduler subscriptionScheduler) {
        super(subscriptionScheduler);
        this.repository=repository;
    }

    @Override
    Observable<List<Quotation>> buildUseCaseObservable(Params params) {
        return repository.getSubscriptions();
    }

    public static class Params {
        private List<String> subscriptions;

        public List<String> getSubscriptions() {
            return subscriptions;
        }

        public void setSubscriptions(List<String> subscriptions) {
            this.subscriptions = subscriptions;
        }

        public Params(List<String> subscriptions) {
            this.subscriptions = subscriptions;
        }
    }
}
