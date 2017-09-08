package com.alexandershtanko.quotations.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.data.utils.ErrorUtils;
import com.alexandershtanko.quotations.domain.models.Quotation;
import com.alexandershtanko.quotations.utils.mvvm.RxViewBinder;
import com.alexandershtanko.quotations.utils.mvvm.RxViewHolder;
import com.alexandershtanko.quotations.view.navigation.Navigator;
import com.alexandershtanko.quotations.viewmodels.QuotationsViewModel;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 08/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationsViewHolder extends RxViewHolder {
    @BindView(R.id.button_instruments)
    Button instrumentsButton;
    @BindView(R.id.button_help)
    Button helpButton;
    @BindView(R.id.list)
    RecyclerView quotations;

    @BindView(R.id.text_symbol)
    View symbol;
    @BindView(R.id.text_bid_ask)
    View bidAsk;
    @BindView(R.id.text_spread)
    View spread;
    @BindView(R.id.text_status)
    TextView status;

    private QuotationsAdapter quotationsAdapter;

    public QuotationsViewHolder(Context context, int layoutRes) {
        super(context, layoutRes);
        quotationsAdapter = new QuotationsAdapter();
        quotations.setLayoutManager(new LinearLayoutManager(context));
        quotations.setAdapter(quotationsAdapter);

    }

    public static class ViewBinder extends RxViewBinder<QuotationsViewHolder, QuotationsViewModel> {
        private final FragmentManager fragmentManager;

        public ViewBinder(FragmentManager fragmentManager, QuotationsViewHolder viewHolder, QuotationsViewModel viewModel) {
            super(viewHolder, viewModel);
            this.fragmentManager = fragmentManager;
        }

        @Override
        protected void onBind(CompositeDisposable s) {
            s.add(RxView.clicks(viewHolder.instrumentsButton).subscribe(v -> Navigator.openInstruments(fragmentManager), ErrorUtils::log));
            s.add(RxView.clicks(viewHolder.helpButton).subscribe(v -> Navigator.openHelp(fragmentManager), ErrorUtils::log));
            s.add(RxView.clicks(viewHolder.symbol).subscribe(v -> viewHolder.quotationsAdapter.setSortType(QuotationsAdapter.SortType.SYMBOL), ErrorUtils::log));
            s.add(RxView.clicks(viewHolder.bidAsk).subscribe(v -> viewHolder.quotationsAdapter.setSortType(QuotationsAdapter.SortType.BID), ErrorUtils::log));
            s.add(RxView.clicks(viewHolder.spread).subscribe(v -> viewHolder.quotationsAdapter.setSortType(QuotationsAdapter.SortType.SPREAD), ErrorUtils::log));

            s.add(viewHolder.quotationsAdapter.getOnRemoveObservable()
                    .observeOn(Schedulers.io())
                    .switchMap(viewModel::removeQuotation)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        Toast.makeText(viewHolder.getContext(), res ? R.string.remove_ok : R.string.remove_failed, Toast.LENGTH_LONG).show();
                    }, ErrorUtils::log));

            s.add(viewModel.getQuotations().throttleLast(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(viewHolder::setQuotations, ErrorUtils::log));
            s.add(viewModel.getConnectionState().throttleLast(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(viewHolder::setConnectionState, ErrorUtils::log));
        }
    }

    private void setConnectionState(Boolean state) {
        status.setText(getContext().getString(R.string.status) + ": " + getContext().getString(state ? R.string.connected : R.string.disconnected));
    }

    private void setQuotations(List<Quotation> quotations) {
        quotationsAdapter.setQuotations(quotations);
    }
}
