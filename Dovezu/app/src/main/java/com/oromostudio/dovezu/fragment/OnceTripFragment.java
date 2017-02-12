package com.oromostudio.dovezu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private List<RequestModel> data;
    private RequestListAdapter adapter;

    public static OnceTripFragment getInstance(Context context){
        Bundle args = new Bundle();

        OnceTripFragment fragment = new OnceTripFragment();
        fragment.setArguments(args);
        fragment.data = new ArrayList<>();
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.onceTrip));

        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.d("ONCE TRIP", "Create view tab");

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(context));
        adapter = new RequestListAdapter(data);
        rv.setAdapter(adapter);

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void refreshData(List<RequestModel> dataList){
        this.data = dataList;
        adapter.setData(dataList);
        adapter.notifyDataSetChanged();
    }
}
