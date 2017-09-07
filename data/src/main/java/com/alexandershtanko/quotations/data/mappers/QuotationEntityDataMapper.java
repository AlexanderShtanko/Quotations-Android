package com.alexandershtanko.quotations.data.mappers;

import com.alexandershtanko.quotations.data.models.QuotationEntity;
import com.alexandershtanko.quotations.data.models.QuotationsResponseEntity;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationEntityDataMapper {

    private static Quotation transform(QuotationEntity quotationEntity) {
        Quotation quotation = new Quotation();
        quotation.setAsk(quotationEntity.getA());
        quotation.setBid(quotationEntity.getB());
        quotation.setSpread(quotationEntity.getSpr());
        quotation.setSymbol(quotationEntity.getS());
        return quotation;
    }

    public static List<Quotation> transform(QuotationsResponseEntity entity) {
        List<Quotation> quotations = new ArrayList<>();
        if (entity != null && entity.getSubscribedList() != null && entity.getSubscribedList().getTicks() != null) {
            for (QuotationEntity quotationEntity : entity.getSubscribedList().getTicks()) {
                quotations.add(transform(quotationEntity));
            }
        }
        return quotations;
    }
}
