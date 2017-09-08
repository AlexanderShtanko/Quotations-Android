package com.alexandershtanko.quotations.view;

import android.content.Context;
import android.view.View;

import com.alexandershtanko.quotations.utils.mvvm.RxViewBinder;
import com.alexandershtanko.quotations.utils.mvvm.RxViewHolder;
import com.alexandershtanko.quotations.viewmodels.InstrumentsViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 08/09/2017.
 *         Copyright Ostrovok.ru
 */

public class InstrumentsViewHolder extends RxViewHolder {
    public InstrumentsViewHolder(Context context, int layoutRes) {
        super(context, layoutRes);
    }

    public InstrumentsViewHolder(View view) {
        super(view);
    }

    public static class ViewBinder extends RxViewBinder<InstrumentsViewHolder,InstrumentsViewModel>{
        public ViewBinder(InstrumentsViewHolder viewHolder, InstrumentsViewModel viewModel) {
            super(viewHolder, viewModel);
        }

        @Override
        protected void onBind(CompositeDisposable s) {

        }
    }
}
