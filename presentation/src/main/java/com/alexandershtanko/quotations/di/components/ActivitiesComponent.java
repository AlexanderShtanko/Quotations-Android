package com.alexandershtanko.quotations.di.components;

import com.alexandershtanko.quotations.di.scopes.ActivityScope;
import com.alexandershtanko.quotations.view.activities.MainActivity;

import dagger.Component;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 05/09/2017.
 *
 */
@Component(dependencies = {ViewModelComponent.class})
@ActivityScope
public interface ActivitiesComponent {
    void inject(MainActivity mainActivity);
}
