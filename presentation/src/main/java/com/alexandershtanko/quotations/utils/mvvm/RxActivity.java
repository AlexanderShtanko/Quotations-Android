package com.alexandershtanko.quotations.utils.mvvm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class RxActivity<H extends RxViewHolder, M extends RxViewModel> extends AppCompatActivity implements ViewViewModel<H, M> {
    H viewHolder;
    M viewModel;

    public RxViewBinder<H, M> getViewBinder() {
        return viewBinder;
    }

    public M getViewModel() {
        return viewModel;
    }

    RxViewBinder<H, M> viewBinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = initViewModel();

        super.onCreate(savedInstanceState);

        viewHolder = initViewHolder();
        setContentView(viewHolder.getView());

        viewBinder = createViewBinder(viewHolder, viewModel);
        viewModel.subscribe();


    }

    @Override
    protected void onPause() {
        super.onPause();
        viewBinder.unbind();

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewBinder.bind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.dispose();
    }


    public H getViewHolder() {
        return viewHolder;
    }
}
