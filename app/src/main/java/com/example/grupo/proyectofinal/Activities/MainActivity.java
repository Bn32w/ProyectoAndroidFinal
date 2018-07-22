package com.example.grupo.proyectofinal.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.grupo.proyectofinal.Adapter.MainActivityPagerAdapter;
import com.example.grupo.proyectofinal.Fragment.MainActivityListviewFragment;
import com.example.grupo.proyectofinal.Model.SalleLocation;
import com.example.grupo.proyectofinal.Model.SalleLocationManager;
import com.example.grupo.proyectofinal.R;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements SalleLocationManager.Viewer,AdapterView.OnItemSelectedListener{

    public static final String SELECTED_SCHOOL_TAG = "Selected_SCHOOL_TAG";
    public static final String SELECTED_SCHOOL_POSITION = "SELECTED_Schhol_Position";

    private MainActivityListviewFragment todo;
    private MainActivityListviewFragment escuelas;
    private MainActivityListviewFragment otros;
    private ProgressDialog dialog;
    private ArrayList<MainActivityPagerAdapter.FragmentBundle> data;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        todo = new MainActivityListviewFragment();
        escuelas = new MainActivityListviewFragment();
        otros = new MainActivityListviewFragment();
        dialog = new ProgressDialog(this);
        createTabs();

        prepareSpinner();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SalleLocationManager.getInstance().fillLocationsFromWebservice(this,false);

    }

    private void prepareSpinner() {
        spinner = findViewById(R.id.main_activity_toolbar_spinner);
        String[] data = getResources().getStringArray(R.array.ciudades);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        spinner.setAdapter(cityAdapter);

        //TODO Listener
        spinner.setOnItemSelectedListener(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_activity_map_toolbar_item:
                Intent intent = new Intent(this,MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_login_toolbar_item:
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
                break;
        }
        return false;
    }

    private void createTabs(){
        TabLayout layout = findViewById(R.id.tabs);
        ViewPager pager = findViewById(R.id.main_activity_list_view_container);

        data = new ArrayList<>();

        MainActivityListviewFragment todo = this.todo;
        data.add(new MainActivityPagerAdapter.FragmentBundle(getString(R.string.todo)
                , todo));
        MainActivityListviewFragment escuelas = this.escuelas;
        data.add(new MainActivityPagerAdapter.FragmentBundle(getString(R.string.escuelas)
                , escuelas));
        MainActivityListviewFragment otros = this.otros;
        data.add(new MainActivityPagerAdapter.FragmentBundle(getString(R.string.otros)
                , otros));

        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(getSupportFragmentManager(), data);
        pager.setAdapter(adapter);
        layout.setupWithViewPager(pager);
    }

    @Override
    public void updateView() {
        todo.notifyDataSetChanged();
        escuelas.notifyDataSetChanged();
        otros.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startProgressBar() {
        dialog.setMessage("Loading data");
        dialog.show();
    }

    @Override
    public void stopProgressBar() {
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        SalleLocationManager.getInstance().sortArray(SalleLocation.sortBarcelona,getString(R.string.Barcelona));

        todo.notifyDataSetChanged();
        otros.notifyDataSetChanged();
        escuelas.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(this);
;
    }

    public ArrayList<MainActivityPagerAdapter.FragmentBundle> getData() {
        return data;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type = parent.getItemAtPosition(position).toString();

        todo.notifyDataSetInvalidated();
        escuelas.notifyDataSetInvalidated();
        otros.notifyDataSetInvalidated();

        SalleLocationManager.getInstance().sortArray(getComparator(type), type);

        todo.notifyDataSetChanged();
        escuelas.notifyDataSetChanged();
        otros.notifyDataSetChanged();
    }

    private Comparator<SalleLocation> getComparator(String type) {
        if(type.equals(getString(R.string.Barcelona))){
            return SalleLocation.sortBarcelona;
        }
        else if(type.equals(getString(R.string.Tarragona))){
            return SalleLocation.sortTarragona;
        }
        else if(type.equals(getString(R.string.Girona))){
            return SalleLocation.sortGirona;
        }
        return SalleLocation.sortLleida;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
