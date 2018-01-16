package com.example.gd.oticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by GD on 1/13/2018.
 */

public class HistoryFrag extends Fragment {

    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize variable
        mainActivity = (MainActivity)this.getActivity();

        //set up fragment
        mainActivity.setNavActiveItem(R.id.nav_history);
        mainActivity.showSearchBar(false);
    }
}
