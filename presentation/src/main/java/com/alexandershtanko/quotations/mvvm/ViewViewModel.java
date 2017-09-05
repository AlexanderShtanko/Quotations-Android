package com.alexandershtanko.quotations.mvvm;

/**
 * Created by aleksandr on 13.06.16.
 */
public interface ViewViewModel<H extends RxViewHolder,M extends RxViewModel> {
    H createViewHolder();
    M createViewModel();
    RxViewBinder<H,M> createViewBinder(H viewHolder, M viewModel);
}
