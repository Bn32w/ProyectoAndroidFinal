package com.example.grupo.proyectofinal.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.example.grupo.proyectofinal.Model.SalleLocation;
import com.example.grupo.proyectofinal.Model.SalleLocationManager;
import com.example.grupo.proyectofinal.R;

public class DetallesActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name;
    private TextView address;
    private TextView[] type;
    private TextView description;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        name = findViewById(R.id.activity_detalles_name);
        address = findViewById(R.id.activity_detalles_direccion);
        type = new TextView[6];
        type[0] = findViewById(R.id.activity_detalles_infantil);
        type[1] = findViewById(R.id.activity_detalles_primaria);
        type[2] = findViewById(R.id.activity_detalles_eso);
        type[3] = findViewById(R.id.activity_detalles_bachillerato);
        type[4] = findViewById(R.id.activity_detalles_fp);
        type[5] = findViewById(R.id.activity_detalles_universidad);
        description = findViewById(R.id.activity_detalles_descripcion);
        Toolbar toolbar = findViewById(R.id.activity_detalles_toolbar);
        FloatingActionButton button = findViewById(R.id.activity_detalles_FAB);
        button.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        int tag = getIntent().getIntExtra(MainActivity.SELECTED_SCHOOL_TAG,0);
        position = getIntent().getIntExtra(MainActivity.SELECTED_SCHOOL_POSITION,0);
        SalleLocation location = SalleLocationManager.getInstance().getLocations(tag).get(position);
        if(location==null){
            return;
        }
        name.setText(location.getName());
        address.setText(location.getAddress());
        description.setText(location.getDescription());
        for(int i=0;i<location.getType().length;i++){
            type[i].setVisibility(location.getType()[i]? View.VISIBLE:View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra(MainActivity.SELECTED_SCHOOL_TAG,R.string.todo);
        intent.putExtra(MainActivity.SELECTED_SCHOOL_POSITION,position);
        startActivity(intent);
    }
}
