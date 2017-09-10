package com.alexandershtanko.quotations.view;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 08/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationsAdapter extends RecyclerView.Adapter<QuotationsAdapter.ViewHolder> {
    private PublishSubject<String> onRemoveSubject = PublishSubject.create();
    private SortedList<Quotation> items;
    private SortType sortType = SortType.SYMBOL;

    public Observable<String> getOnRemoveObservable() {
        return onRemoveSubject;
    }

    public enum SortType {SYMBOL, BID, SPREAD}

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
        List<Quotation> list = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            list.add(items.get(i));
        }
        items.beginBatchedUpdates();
        items.clear();
        items.addAll(list);
        items.endBatchedUpdates();
    }

    public QuotationsAdapter() {
        items = new SortedList<>(Quotation.class, new SortedList.BatchedCallback<>(new SortedList.Callback<Quotation>() {
            @Override
            public int compare(Quotation o1, Quotation o2) {
                switch (sortType) {
                    case SYMBOL:
                        return o1.getSymbol().compareTo(o2.getSymbol());
                    case BID:
                        return Float.compare(Float.parseFloat(o1.getBid()), Float.parseFloat(o2.getBid()));
                    case SPREAD:
                        return Float.compare(Float.parseFloat(o1.getSpread()), Float.parseFloat(o2.getSpread()));
                }
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Quotation oldItem, Quotation newItem) {
                return areItemsTheSame(oldItem, newItem)
                        && oldItem.getAsk().equals(newItem.getAsk())
                        && oldItem.getBid().equals(newItem.getBid())
                        && oldItem.getSpread().equals(newItem.getBid());
            }

            @Override
            public boolean areItemsTheSame(Quotation item1, Quotation item2) {
                return item1.getSymbol().equals(item2.getSymbol());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);

            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        }));
    }

    public void setQuotations(List<Quotation> quotations) {
        items.beginBatchedUpdates();
        for (Quotation quotation : quotations) {
            items.add(quotation);
        }
        List<Quotation> toRemove = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            boolean flgFound = false;
            for (Quotation quotation : quotations) {
                if (items.get(i).getSymbol().equals(quotation.getSymbol())) {
                    items.add(quotation);
                    flgFound = true;
                    break;
                }
            }
            if (!flgFound) {
                toRemove.add(items.get(i));
            }
        }

        for (Quotation quotation : toRemove) {
            items.remove(quotation);
        }

        items.endBatchedUpdates();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quotations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populate(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_symbol)
        TextView symbol;
        @BindView(R.id.text_bid_ask)
        TextView bidAsk;
        @BindView(R.id.text_spread)
        TextView spread;
        @BindView(R.id.button_remove)
        View removeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populate(Quotation quotation) {
            removeButton.setOnClickListener(v -> onRemoveSubject.onNext(quotation.getSymbol()));
            symbol.setText(quotation.getSymbol());
            bidAsk.setText(quotation.getBid() + " / " + quotation.getAsk());
            spread.setText(quotation.getSpread());
        }
    }
}
