package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.view.fragments.InstrumentsFragment;
import com.alexandershtanko.quotations.view.fragments.QuotationsFragment;

import dagger.Component;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */
@Component(dependencies = {AppComponent.class, ViewModelComponent.class,UseCaseComponent.class})
public interface FragmentsComponent {
    void inject(QuotationsFragment quotationsFragment);
    void inject(InstrumentsFragment instrumentsFragment);

}
