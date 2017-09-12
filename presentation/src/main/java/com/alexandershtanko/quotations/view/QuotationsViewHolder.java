package com.alexandershtanko.quotations.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 08/09/2017.
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

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return quotationsAdapter.onItemMove(viewHolder.getAdapterPosition(),
                    target.getAdapterPosition());
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            quotationsAdapter.onItemDismiss(viewHolder.getAdapterPosition());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }
    });

    private QuotationsAdapter quotationsAdapter;

    public QuotationsViewHolder(Context context, int layoutRes) {
        super(context, layoutRes);
        quotationsAdapter = new QuotationsAdapter();
        quotations.setLayoutManager(new LinearLayoutManager(context));
        quotations.setAdapter(quotationsAdapter);
        quotations.setItemAnimator(null);
        itemTouchHelper.attachToRecyclerView(quotations);


    }

    public static class ViewBinder extends RxViewBinder<QuotationsViewHolder, QuotationsViewModel> {
        private final FragmentManager fragmentManager;

        public ViewBinder(FragmentManager fragmentManager, QuotationsViewHolder viewHolder, QuotationsViewModel viewModel) {
            super(viewHolder, viewModel);
            this.fragmentManager = fragmentManager;
        }

        @Override
        protected void onBind(CompositeDisposable s) {
            s.add(RxView.clicks(viewHolder.instrumentsButton).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(v -> Navigator.openInstruments(fragmentManager), ErrorUtils::log));
            s.add(RxView.clicks(viewHolder.helpButton).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(v -> Navigator.openHelp(fragmentManager), ErrorUtils::log));

            s.add(RxView.clicks(viewHolder.symbol).throttleFirst(500,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(v -> viewHolder.quotationsAdapter.sortBySymbol(), ErrorUtils::log));
            s.add(RxView.clicks(viewHolder.bidAsk).throttleFirst(500,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(v -> viewHolder.quotationsAdapter.sortByBid(), ErrorUtils::log));
            s.add(RxView.clicks(viewHolder.spread).throttleFirst(500,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(v -> viewHolder.quotationsAdapter.sortBySpread(), ErrorUtils::log));

            s.add(viewHolder.quotationsAdapter.getOnRemoveObservable()
                    .observeOn(Schedulers.io())
                    .switchMap(viewModel::removeQuotation)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        Toast.makeText(viewHolder.getContext(), res ? R.string.remove_ok : R.string.remove_failed, Toast.LENGTH_LONG).show();
                    }, ErrorUtils::log));

            s.add(viewModel.getQuotations().toFlowable(BackpressureStrategy.LATEST).observeOn(AndroidSchedulers.mainThread()).subscribe(viewHolder::setQuotations, ErrorUtils::log));
            s.add(viewModel.getConnectionState().throttleLast(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(viewHolder::setConnectionState, ErrorUtils::log));
        }
    }

    private void setConnectionState(Boolean state) {
        status.setText(getContext().getString(R.string.status) + ": " + getContext().getString(state ? R.string.connected : R.string.disconnected));
    }

    private void setQuotations(List<Quotation> quotations) {
        quotationsAdapter.setQuotations(quotations);
    }
}
