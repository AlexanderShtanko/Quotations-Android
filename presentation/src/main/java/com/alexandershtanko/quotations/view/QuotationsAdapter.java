package com.alexandershtanko.quotations.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.data.utils.ErrorUtils;
import com.alexandershtanko.quotations.domain.models.Quotation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 08/09/2017.
 */

public class QuotationsAdapter extends RecyclerView.Adapter<QuotationsAdapter.ViewHolder> {
    private PublishSubject<String> onRemoveSubject = PublishSubject.create();
    private List<Quotation> items = new ArrayList<>();

    public Observable<String> getOnRemoveObservable() {
        return onRemoveSubject.hide();
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;

    }

    public void onItemDismiss(int adapterPosition) {
        try {
            onRemoveSubject.onNext(items.get(adapterPosition).getSymbol());
            items.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
        } catch (Exception e) {
            ErrorUtils.log(e);
        }
    }


    public synchronized void setQuotations(List<Quotation> quotations) {

        List<Quotation> toRemoveList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            boolean flgFound = false;
            for (Quotation quotation : quotations) {
                if (quotation.getSymbol().equals(items.get(i).getSymbol())) {
                    flgFound = true;
                    break;
                }
            }
            if (!flgFound)
                toRemoveList.add(items.get(i));
        }


        for (Quotation quotation : toRemoveList) {
            int pos = items.indexOf(quotation);
            items.remove(quotation);
            notifyItemRemoved(pos);
        }


        for (Quotation quotation : quotations) {
            boolean flgFound = false;
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getSymbol().equals(quotation.getSymbol())) {
                    items.set(i, quotation);
                    notifyItemChanged(i);
                    flgFound = true;
                    break;
                }
            }
            if (!flgFound) {
                items.add(quotation);
                notifyItemInserted(items.size() - 1);
            }
        }


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

    public void sortBySymbol() {
        Collections.sort(items, (q1, q2) -> q1.getSymbol().compareTo(q2.getSymbol()));
        notifyDataSetChanged();
    }

    public void sortByBid() {
        Collections.sort(items, this::compareBid);
        notifyDataSetChanged();
    }

    private int compareBid(Quotation o1, Quotation o2) {
        try {
            return Float.compare(Float.parseFloat(o1.getBid()), Float.parseFloat(o2.getBid()));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    ;

    public void sortBySpread() {
        Collections.sort(items, this::compareSpread);
        notifyDataSetChanged();
    }

    private int compareSpread(Quotation o1, Quotation o2) {
        try {
            return Float.compare(Float.parseFloat(o1.getBid()), Float.parseFloat(o2.getBid()));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    ;

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
            removeButton.setOnClickListener(v -> {
                onItemDismiss(getAdapterPosition());
            });
            symbol.setText(quotation.getSymbol());
            bidAsk.setText(quotation.getBid() + " / " + quotation.getAsk());
            spread.setText(quotation.getSpread());

        }
    }
}
