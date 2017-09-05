package com.alexandershtanko.quotations.domain.repository;

import com.alexandershtanko.quotations.domain.interactor.AddSubscriptionUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveSubscriptionUseCase;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */

public interface SubscriptionsRepository {
    Observable<List<Quotation>> getSubscriptions();
    Completable addSubscriptions(AddSubscriptionUseCase.Params params);
    Completable removeSubscription(RemoveSubscriptionUseCase.Params params);
}
