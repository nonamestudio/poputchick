package com.oromostudio.dovezu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oromostudio.dovezu.R;


public class OnceTripFragment extends AbstractTabFragment{
    private static final int LAYOUT = R.layout.login_layout;

    public static OnceTripFragment getInstance(Context context){
        Bundle args = new Bundle();

        OnceTripFragment fragment = new OnceTripFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.onceTrip));

        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
