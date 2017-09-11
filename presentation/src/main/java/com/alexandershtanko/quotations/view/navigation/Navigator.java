package com.alexandershtanko.quotations.view.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.data.utils.ErrorUtils;
import com.alexandershtanko.quotations.view.fragments.HelpFragment;
import com.alexandershtanko.quotations.view.fragments.InstrumentsFragment;
import com.alexandershtanko.quotations.view.fragments.QuotationsFragment;

/**
 * @author Alexander Shtanko alexjcomp@gmail.com
 *         Created on 08/09/2017.
 *
 */

public class Navigator {
    private static final String TAG = Navigator.class.getSimpleName();

    public static void openQuotations(FragmentManager fragmentManager) {
        addFragment(fragmentManager, new QuotationsFragment(), QuotationsFragment.class.getSimpleName(), true);
    }

    public static void openInstruments(FragmentManager fragmentManager) {
        addFragment(fragmentManager, new InstrumentsFragment(), InstrumentsFragment.class.getSimpleName(), true);
    }

    public static void openHelp(FragmentManager fragmentManager) {
        addFragment(fragmentManager, new HelpFragment(), HelpFragment.class.getSimpleName(), true);
    }


    private static void addFragment(FragmentManager fragmentManager, Fragment fragment, String tag, boolean toBackStack) {
        try {
            Fragment old = fragmentManager.findFragmentByTag(tag);

            if (old != null)
                fragmentManager.beginTransaction().remove(old).commitAllowingStateLoss();

            FragmentTransaction transaction = fragmentManager.beginTransaction().add(R.id.layout_content, fragment, tag);

            if (toBackStack) {
                fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction.addToBackStack(tag);
            }

            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            ErrorUtils.log(TAG, e);
        }


    }


}
