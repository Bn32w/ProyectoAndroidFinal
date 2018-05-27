package com.example.grupo.proyectofinal.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.grupo.proyectofinal.Adapter.MapAdapter;
import com.example.grupo.proyectofinal.Model.SalleLocation;
import com.example.grupo.proyectofinal.Model.SalleLocationManager;
import com.example.grupo.proyectofinal.R;
import com.google.android.gms.maps.SupportMapFragment;

import org.w3c.dom.Text;


public class MapsActivity extends AppCompatActivity implements MapAdapter.IntUpdater{

    private BottomSheetBehavior sheetBehavior;
    private int selectedPosition;
    private SalleLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = findViewById(R.id.map_toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.map_fragment,mapFragment).commit();
        mapFragment.getMapAsync(new MapAdapter(SalleLocationManager.getInstance().getLocations(R.string.todo),this));
        Spinner spinner = findViewById(R.id.map_spinner);
        String[] data = {getString(R.string.todo),getString(R.string.escuelas),getString(R.string.otros)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        spinner.setAdapter(adapter);

        sheetBehavior = BottomSheetBehavior.from(findViewById(R.id.map_bottom_sheet));
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        TextView name = bottomSheet.findViewById(R.id.map_fragment_descriptor_name);
                        TextView address = bottomSheet.findViewById(R.id.map_fragment_descriptor_address);
                        name.setText(location.getName());
                        address.setText(location.getAddress());
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if(slideOffset>=0.5){
                    Intent intent = new Intent(MapsActivity.this,DetallesActivity.class);
                    intent.putExtra(MainActivity.SELECTED_SCHOOL_TAG,R.string.todo);
                    intent.putExtra(MainActivity.SELECTED_SCHOOL_POSITION,selectedPosition);
                    MapsActivity.this.setIntent(intent);
                }
            }
        });
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

        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Log.d(MapsActivity.class.getCanonicalName(),location.getName());
    }

}
