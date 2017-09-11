package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.di.scopes.FragmentsScope;
import com.alexandershtanko.quotations.view.fragments.InstrumentsFragment;
import com.alexandershtanko.quotations.view.fragments.QuotationsFragment;

import dagger.Component;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 05/09/2017.
 *
 */
@Component(dependencies = {ViewModelComponent.class})
@FragmentsScope
public interface FragmentsComponent {
    void inject(QuotationsFragment quotationsFragment);
    void inject(InstrumentsFragment instrumentsFragment);

}
