package com.alexandershtanko.quotations.view.fragments;

import com.alexandershtanko.quotations.App;
import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.utils.mvvm.RxFragment;
import com.alexandershtanko.quotations.utils.mvvm.RxViewBinder;
import com.alexandershtanko.quotations.view.QuotationsViewHolder;
import com.alexandershtanko.quotations.viewmodels.QuotationsViewModel;

import javax.inject.Inject;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 08/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationsFragment extends RxFragment<QuotationsViewHolder, QuotationsViewModel> {
    @Inject
    QuotationsViewModel viewModel;


    @Override
    public QuotationsViewHolder initViewHolder() {
        return new QuotationsViewHolder(getMainActivity(), R.layout.fragment_quotations);
    }

    @Override
    public QuotationsViewModel initViewModel()
    {
        App.getComponent().inject(this);
        return viewModel;
    }

    @Override
    public RxViewBinder<QuotationsViewHolder, QuotationsViewModel> createViewBinder(QuotationsViewHolder viewHolder, QuotationsViewModel viewModel) {
        return new QuotationsViewHolder.ViewBinder(getFragmentManager(), viewHolder, viewModel);
    }
}
