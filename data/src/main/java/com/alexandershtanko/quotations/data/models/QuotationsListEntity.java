package com.alexandershtanko.quotations.data.models;

import java.util.List;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationsListEntity {
    private List<QuotationEntity> ticks;

    public List<QuotationEntity> getTicks() {
        return ticks;
    }

    public void setTicks(List<QuotationEntity> ticks) {
        this.ticks = ticks;
    }
}
