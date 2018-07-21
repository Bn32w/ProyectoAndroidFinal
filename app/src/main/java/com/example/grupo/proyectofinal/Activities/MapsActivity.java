package com.example.grupo.proyectofinal.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.grupo.proyectofinal.Adapter.MapAdapter;
import com.example.grupo.proyectofinal.Model.SalleLocation;
import com.example.grupo.proyectofinal.Model.SalleLocationManager;
import com.example.grupo.proyectofinal.R;
import com.google.android.gms.maps.SupportMapFragment;

import org.w3c.dom.Text;

import java.util.Map;


public class MapsActivity extends AppCompatActivity implements MapAdapter.IntUpdater, AdapterView.OnItemSelectedListener {

    private BottomSheetBehavior sheetBehavior;
    private int selectedPosition;
    private SalleLocation location=null;
    private static TextView name,address;
    private MapAdapter mapReadyCallback;
    private int locationState;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationState = R.string.todo;
        name=null;
        address=null;
        Toolbar toolbar = findViewById(R.id.map_toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapReadyCallback = new MapAdapter(this,locationState);
        mapFragment.getMapAsync(mapReadyCallback);

        spinner = findViewById(R.id.map_spinner);
        String[] data = {getString(R.string.todo),getString(R.string.escuelas),getString(R.string.otros)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.map_bottom_sheet));
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        TextView name = bottomSheet.findViewById(R.id.map_fragment_descriptor_name);
                        TextView address = bottomSheet.findViewById(R.id.map_fragment_descriptor_address);
                        MapsActivity.this.name=name;
                        MapsActivity.this.address=address;
                        name.setText(location.getName());
                        address.setText(location.getAddress());
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Intent intent = new Intent(MapsActivity.this,DetallesActivity.class);
                        intent.putExtra(MainActivity.SELECTED_SCHOOL_TAG,R.string.todo);
                        intent.putExtra(MainActivity.SELECTED_SCHOOL_POSITION,selectedPosition);
                        MapsActivity.this.sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        MapsActivity.this.startActivity(intent);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.SELECTED_SCHOOL_POSITION)){
            mapReadyCallback.clickMarker(intent.getIntExtra(MainActivity.SELECTED_SCHOOL_POSITION,0));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.map_activity_go_back:
                finish();
                break;
        }
        return false;
    }

    @Override
    public void updateInterface(SalleLocation location, int selectedPosition) {
        this.selectedPosition = selectedPosition;
        this.location = location;

        if(name!=null&&address!=null){
            name.setText(location.getName());
            address.setText(location.getAddress());
        }
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Log.d(MapsActivity.class.getCanonicalName(),location.getName());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mapReadyCallback.changeTargets(getSelectedTag(spinner.getSelectedItem().toString()));
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private int getSelectedTag(String s) {
        if(s.equals(getString(R.string.todo))){
            return R.string.todo;
        }
        else if(s.equals(getString(R.string.escuelas))){
            return R.string.escuelas;
        }else{
            return R.string.otros;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
