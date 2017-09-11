package com.alexandershtanko.quotations.data.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by aleksandr on 10.09.17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
}
