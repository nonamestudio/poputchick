package com.oromostudio.dovezu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oromostudio.dovezu.R;
import com.oromostudio.dovezu.adapter.RequestListAdapter;
import com.oromostudio.dovezu.models.RequestModel;

import java.util.ArrayList;
import java.util.List;


public class OnceTripFragment extends AbstractTabFragment{
    private static final int LAYOUT = R.layout.fragment_once_trip;

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

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new RequestListAdapter(getRequests()));

        return view;
    }

    private List<RequestModel> getRequests() {

        List<RequestModel> result = new ArrayList<>();

        return result;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
