package com.alexandershtanko.quotations.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexandershtanko.quotations.R;
import com.alexandershtanko.quotations.data.utils.ErrorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 08/09/2017.
 *         Copyright Ostrovok.ru
 */

public class HelpFragment extends Fragment {
    @BindView(R.id.button_back)
    View backButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);
        backButton.setOnClickListener(v -> {
            try {
                getFragmentManager().popBackStack();
            } catch (Exception e) {
                ErrorUtils.log(e);
            }
        });

        return view;

    }
}
