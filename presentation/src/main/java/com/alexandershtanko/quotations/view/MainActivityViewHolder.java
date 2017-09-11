package com.alexandershtanko.quotations.view;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alexandershtanko.quotations.utils.mvvm.RxViewBinder;
import com.alexandershtanko.quotations.utils.mvvm.RxViewHolder;
import com.alexandershtanko.quotations.view.navigation.Navigator;
import com.alexandershtanko.quotations.viewmodels.MainActivityViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 08/09/2017.
 *
 */

public class MainActivityViewHolder extends RxViewHolder {
    public MainActivityViewHolder(AppCompatActivity activity, int layoutRes) {
        super(activity, layoutRes);
        Navigator.openQuotations(activity.getSupportFragmentManager());
    }

    public MainActivityViewHolder(View view) {
        super(view);
    }

    public static class ViewBinder extends RxViewBinder<MainActivityViewHolder, MainActivityViewModel> {
        public ViewBinder(MainActivityViewHolder viewHolder, MainActivityViewModel viewModel) {
            super(viewHolder, viewModel);
        }

        @Override
        protected void onBind(CompositeDisposable s) {

        }
    }
}
