package com.alexandershtanko.quotations.view.fragments;

import com.alexandershtanko.quotations.App;
import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.utils.mvvm.RxFragment;
import com.alexandershtanko.quotations.utils.mvvm.RxViewBinder;
import com.alexandershtanko.quotations.view.InstrumentsViewHolder;
import com.alexandershtanko.quotations.viewmodels.InstrumentsViewModel;

import javax.inject.Inject;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 08/09/2017.
 *
 */

public class InstrumentsFragment extends RxFragment<InstrumentsViewHolder, InstrumentsViewModel> {
    @Inject
    InstrumentsViewModel viewModel;

    @Override
    public InstrumentsViewHolder initViewHolder() {
        return new InstrumentsViewHolder(getMainActivity(), R.layout.fragment_instruments);
    }
    @Override
    public InstrumentsViewModel initViewModel() {
        App.getFragmentsComponent().inject(this);
        return viewModel;
    }

    @Override
    public RxViewBinder<InstrumentsViewHolder, InstrumentsViewModel> createViewBinder(InstrumentsViewHolder viewHolder, InstrumentsViewModel viewModel) {
        return new InstrumentsViewHolder.ViewBinder(getFragmentManager(),viewHolder, viewModel);
    }


}
