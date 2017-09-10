package com.alexandershtanko.quotations.di.components;

import android.content.Context;

import com.alexandershtanko.quotations.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 05/09/2017.
 *         Copyright Ostrovok.ru
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    Context context();
}
