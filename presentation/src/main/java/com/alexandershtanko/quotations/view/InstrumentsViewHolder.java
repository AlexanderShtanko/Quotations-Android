package com.alexandershtanko.quotations.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.data.utils.ErrorUtils;
import com.alexandershtanko.quotations.utils.mvvm.RxViewBinder;
import com.alexandershtanko.quotations.utils.mvvm.RxViewHolder;
import com.alexandershtanko.quotations.viewmodels.InstrumentsViewModel;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 08/09/2017.
 *
 */

public class InstrumentsViewHolder extends RxViewHolder {

    @BindView(R.id.layout_instruments)
    ViewGroup instrumentsLayout;
    @BindView(R.id.button_back)
    View backButton;

    public InstrumentsViewHolder(Context context, int layoutRes) {
        super(context, layoutRes);
    }

    public InstrumentsViewHolder(View view) {
        super(view);
    }

    public static class ViewBinder extends RxViewBinder<InstrumentsViewHolder, InstrumentsViewModel> {
        private final FragmentManager fragmentManager;

        public ViewBinder(FragmentManager fragmentManager, InstrumentsViewHolder viewHolder, InstrumentsViewModel viewModel) {
            super(viewHolder, viewModel);
            this.fragmentManager = fragmentManager;
        }

        @Override
        protected void onBind(CompositeDisposable s) {
            s.add(viewModel.getInstruments()
                    .switchMap(instruments -> viewModel.getSelectedInstruments()
                            .map(selected -> new Pair<>(instruments, selected))).firstElement().toObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::populate, ErrorUtils::log));

            s.add(viewModel.getAddResult()
                    .throttleLast(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        Toast.makeText(viewHolder.getContext(), res ? R.string.add_ok : R.string.add_failed, Toast.LENGTH_SHORT).show();
                    }, ErrorUtils::log));

            s.add(viewModel.getRemoveResult()
                    .throttleLast(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        Toast.makeText(viewHolder.getContext(), res ? R.string.remove_ok : R.string.remove_failed, Toast.LENGTH_SHORT).show();
                    }, ErrorUtils::log));

            s.add(RxView.clicks(viewHolder.backButton).subscribe(v -> goBack(), ErrorUtils::log));
        }

        public boolean goBack() {
            return fragmentManager.popBackStackImmediate();
        }

        private void populate(Pair<List<String>, List<String>> pair) {
            List<String> instruments = pair.first;
            List<String> selected = pair.second;
            viewHolder.instrumentsLayout.removeAllViews();
            for (String instrument : instruments) {
                CheckBox checkBox = (CheckBox) LayoutInflater.from(viewHolder.getContext()).inflate(R.layout.item_instrument, viewHolder.instrumentsLayout, false);
                checkBox.setText(instrument);
                checkBox.setChecked(selected.contains(instrument));
                checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    if (isChecked)
                        viewModel.addInstrument(instrument);
                    else
                        viewModel.removeInstrument(instrument);
                });

                viewHolder.instrumentsLayout.addView(checkBox);
            }
        }
    }
}
