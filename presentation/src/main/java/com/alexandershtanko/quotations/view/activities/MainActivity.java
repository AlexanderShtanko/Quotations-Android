package com.alexandershtanko.quotations.view.activities;

import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.utils.mvvm.RxActivity;
import com.alexandershtanko.quotations.utils.mvvm.RxViewBinder;
import com.alexandershtanko.quotations.view.MainActivityViewHolder;
import com.alexandershtanko.quotations.viewmodels.MainActivityViewModel;

import javax.inject.Inject;

public class MainActivity extends RxActivity<MainActivityViewHolder, MainActivityViewModel> {
    @Inject
    MainActivityViewModel viewModel;

    @Override
    public MainActivityViewHolder initViewHolder() {
        return new MainActivityViewHolder(this, R.layout.activity_main);
    }

    @Override
    public MainActivityViewModel initViewModel() {
        return viewModel;
    }

    @Override
    public RxViewBinder<MainActivityViewHolder, MainActivityViewModel> createViewBinder(MainActivityViewHolder viewHolder, MainActivityViewModel viewModel) {
        return new MainActivityViewHolder.ViewBinder(viewHolder, viewModel);
    }
}
