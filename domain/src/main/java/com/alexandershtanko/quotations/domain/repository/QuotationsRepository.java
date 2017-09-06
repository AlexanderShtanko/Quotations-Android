package com.alexandershtanko.quotations.domain.repository;

import com.alexandershtanko.quotations.domain.interactor.AddQuotationUseCase;
import com.alexandershtanko.quotations.domain.interactor.GetQuotationsUseCase;
import com.alexandershtanko.quotations.domain.interactor.RemoveQuotationUseCase;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */

public interface QuotationsRepository {
    Observable<List<Quotation>> getQuotations(GetQuotationsUseCase.Params params);
    Observable<AddQuotationUseCase.Result> addQuotation(AddQuotationUseCase.Params params);
    Observable<RemoveQuotationUseCase.Result> removeQuotation(RemoveQuotationUseCase.Params params);
}
