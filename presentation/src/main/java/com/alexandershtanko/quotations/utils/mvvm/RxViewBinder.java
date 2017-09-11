package com.alexandershtanko.quotations.utils.mvvm;


import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by aleksandr on 12.06.16.
 */
public abstract class RxViewBinder<H extends RxViewHolder, M extends RxViewModel> implements ViewBinder {
    protected final H viewHolder;
    protected final M viewModel;

    CompositeDisposable s;

    public RxViewBinder(H viewHolder, M viewModel) {
        this.viewHolder = viewHolder;
        this.viewModel = viewModel;
    }

    @Override
    public void bind() {
        s = new CompositeDisposable();
        onBind(s);
    }

    @Override
    public void unbind() {
        s.dispose();
        s = null;
    }

    protected abstract void onBind(CompositeDisposable s);


}
