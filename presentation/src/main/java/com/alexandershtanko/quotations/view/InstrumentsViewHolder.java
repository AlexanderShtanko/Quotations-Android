package com.alexandershtanko.quotations.view;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.data.utils.ErrorUtils;
import com.alexandershtanko.quotations.utils.mvvm.RxViewBinder;
import com.alexandershtanko.quotations.utils.mvvm.RxViewHolder;
import com.alexandershtanko.quotations.viewmodels.InstrumentsViewModel;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 08/09/2017.
 *         Copyright Ostrovok.ru
 */

public class InstrumentsViewHolder extends RxViewHolder {

    @BindView(R.id.layout_instruments)
    ViewGroup instrumentsLayout;

    public InstrumentsViewHolder(Context context, int layoutRes) {
        super(context, layoutRes);
    }

    public InstrumentsViewHolder(View view) {
        super(view);
    }

    public static class ViewBinder extends RxViewBinder<InstrumentsViewHolder, InstrumentsViewModel> {
        public ViewBinder(InstrumentsViewHolder viewHolder, InstrumentsViewModel viewModel) {
            super(viewHolder, viewModel);
        }

        @Override
        protected void onBind(CompositeDisposable s) {
            s.add(viewModel.getInstruments()
                    .switchMap(instruments -> viewModel.getSelectedInstruments()
                            .map(selected -> new Pair<>(instruments, selected))).firstElement().toObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::populate, ErrorUtils::log));
        }

        private void populate(Pair<List<String>, List<String>> pair) {
            List<String> instruments = pair.first;
            List<String> selected = pair.second;

            for (String instrument : instruments) {
                CheckBox checkBox = (CheckBox) LayoutInflater.from(viewHolder.getContext()).inflate(R.layout.item_instrument, viewHolder.instrumentsLayout, false);
                checkBox.setText(instrument);
                checkBox.setChecked(selected.contains(instrument));
                checkBox.setOnClickListener(view -> {
                    boolean isChecked = checkBox.isChecked();
                    if (isChecked)
                        viewModel.addInstrument(instrument);
                    else
                        viewModel.removeInstrument(instrument);


                });
            }
        }
    }
}
