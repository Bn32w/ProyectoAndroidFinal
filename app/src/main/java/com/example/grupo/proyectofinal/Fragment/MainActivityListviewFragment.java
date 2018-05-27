package com.example.grupo.proyectofinal.Fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.grupo.proyectofinal.Activities.MainActivity;
import com.example.grupo.proyectofinal.Adapter.MainActivityListAdapter;
import com.example.grupo.proyectofinal.Adapter.MainActivityPagerAdapter;
import com.example.grupo.proyectofinal.Model.SalleLocation;
import com.example.grupo.proyectofinal.Model.SalleLocationManager;
import com.example.grupo.proyectofinal.R;

import java.util.ArrayList;

/**
 * Created by bn32w on 15/05/18.
 */

public class MainActivityListviewFragment extends ListFragment {

    private MainActivityListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_tabbed_list_view,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int data = findData();
        adapter = new MainActivityListAdapter(getActivity(),SalleLocationManager.getInstance(), data);
        getListView().setAdapter(adapter);
        getListView().setOnItemClickListener(adapter);
        adapter.notifyDataSetChanged();
    }

    private int findData() {
        MainActivity activity = (MainActivity)getActivity();
        ArrayList<MainActivityPagerAdapter.FragmentBundle> data = activity.getData();
        String name="";
        for(MainActivityPagerAdapter.FragmentBundle bundle:data){
            if(bundle.getFragment()==this){
                name = bundle.getName();
                break;
            }
        }
        if(name.equals(getString(R.string.todo))){
            return R.string.todo;
        }
        if(name.equals(getString(R.string.escuelas))){
            return R.string.escuelas;
        }
        if(name.equals(getString(R.string.otros))){
            return R.string.otros;
        }
        return -1;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if(adapter==null){
            return;
        }
        adapter.notifyDataSetChanged();
    }

    public void notifyDataSetInvalidated() {
        if(adapter==null){
            return;
        }
        adapter.notifyDataSetInvalidated();
    }
}
