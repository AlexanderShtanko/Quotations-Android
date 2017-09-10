package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.view.activities.MainActivity;

import dagger.Component;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */
@Component(dependencies = {AppComponent.class, ViewModelComponent.class,UseCaseComponent.class})
public interface ActivitiesComponent {
    void inject(MainActivity mainActivity);
}
