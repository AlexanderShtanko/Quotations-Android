package com.alexandershtanko.quotations.utils.mvvm;

/**
 * Created by aleksandr on 13.06.16.
 */
public interface ViewViewModel<H extends RxViewHolder,M extends RxViewModel> {
    H initViewHolder();
    M initViewModel();
    RxViewBinder<H,M> createViewBinder(H viewHolder, M viewModel);
}
