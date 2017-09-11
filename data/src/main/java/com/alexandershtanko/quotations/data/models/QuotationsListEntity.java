package com.alexandershtanko.quotations.data.models;

import java.util.List;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 06/09/2017.
 *
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
