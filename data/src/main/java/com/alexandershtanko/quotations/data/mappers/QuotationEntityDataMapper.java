package com.alexandershtanko.quotations.data.mappers;

import com.alexandershtanko.quotations.data.models.QuotationEntity;
import com.alexandershtanko.quotations.domain.models.Quotation;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

@Singleton
public class QuotationEntityDataMapper {

    @Inject
    QuotationEntityDataMapper() {
    }

    public Quotation transform(QuotationEntity quotationEntity) {
        Quotation quotation = new Quotation();
        quotation.setAsk(quotationEntity.getA());
        quotation.setBid(quotationEntity.getB());
        quotation.setSpread(quotationEntity.getSpr());
        quotation.setSymbol(quotationEntity.getS());
        return quotation;
    }
}
