package com.alexandershtanko.quotations.data.models;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationsResponseEntity {
    private int subscribedCount;
    private QuotationsListEntity subscribedList;

    public int getSubscribedCount() {
        return subscribedCount;
    }

    public void setSubscribedCount(int subscribedCount) {
        this.subscribedCount = subscribedCount;
    }

    public QuotationsListEntity getSubscribedList() {
        return subscribedList;
    }

    public void setSubscribedList(QuotationsListEntity subscribedList) {
        this.subscribedList = subscribedList;
    }
}
