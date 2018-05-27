package com.example.grupo.proyectofinal.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by bn32w on 15/05/18.
 */

public class MainActivityPagerAdapter extends FragmentPagerAdapter {


    private List<FragmentBundle> bundleList;

    public static class FragmentBundle{
        private String name;
        private Fragment fragment;

        public FragmentBundle(String name, Fragment fragment){
            this.name = name;
            this.fragment = fragment;
        }

        public Fragment getFragment(){
            return fragment;
        }

        public String getName(){
            return name;
        }
    }

    public MainActivityPagerAdapter(FragmentManager fm, List<FragmentBundle> bundleList) {
        super(fm);
        this.bundleList = bundleList;
    }

    @Override
    public Fragment getItem(int position) {
        return bundleList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return bundleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return bundleList.get(position).getName();
    }
}
