package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.di.modules.AppModule;
import com.alexandershtanko.quotations.view.fragments.InstrumentsFragment;
import com.alexandershtanko.quotations.view.fragments.QuotationsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */
@Component(modules = {AppModule.class}, dependencies = {ViewModelComponent.class})
@Singleton
public interface AppComponent {
    void inject(QuotationsFragment quotationsFragment);
    void inject(InstrumentsFragment instrumentsFragment);

}
