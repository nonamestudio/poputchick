package com.oromostudio.dovezu.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oromostudio.dovezu.fragment.AbstractTabFragment;
import com.oromostudio.dovezu.fragment.ConstantlyTripFragment;
import com.oromostudio.dovezu.fragment.IntercityTripFragment;
import com.oromostudio.dovezu.fragment.OnceTripFragment;
import com.oromostudio.dovezu.models.RequestModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter{

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;
    private OnceTripFragment onceTripFragment;
    private ConstantlyTripFragment constantlyTripFragment;
    private IntercityTripFragment intercityTripFragment;

    public TabsPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        initTabs();

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabs() {

        onceTripFragment = OnceTripFragment.getInstance(context);
        constantlyTripFragment = ConstantlyTripFragment.getInstance(context);
        intercityTripFragment = IntercityTripFragment.getInstance(context);


        tabs = new HashMap<>();
        tabs.put(0, onceTripFragment);
        tabs.put(1, constantlyTripFragment);
        tabs.put(2, intercityTripFragment);
    }

    public void setData(List<RequestModel> data){
        onceTripFragment.refreshData(data);
        //constantlyTripFragment.refreshData(data);
        //intercityTripFragment.refreshData(data);
    }
}
