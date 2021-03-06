package com.alexandershtanko.quotations.data.utils;

import android.util.Log;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 07/09/2017.
 *
 */

public class ErrorUtils {


    public static void log(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    public static void log(Throwable throwable) {
        Log.e(ErrorUtils.class.getSimpleName(), "", throwable);

    }

    public static void log(String tag, Throwable throwable) {
        Log.e(tag, "", throwable);


    }
}
