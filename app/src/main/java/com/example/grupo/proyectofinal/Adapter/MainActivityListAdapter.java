package com.example.grupo.proyectofinal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.grupo.proyectofinal.Activities.DetallesActivity;
import com.example.grupo.proyectofinal.Activities.MainActivity;
import com.example.grupo.proyectofinal.Model.SalleLocation;
import com.example.grupo.proyectofinal.Model.SalleLocationManager;
import com.example.grupo.proyectofinal.R;

import java.util.List;

/**
 * Created by bn32w on 15/05/18.
 */

public class MainActivityListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private Context context;
    private SalleLocationManager locations;
    private int tag;

    public MainActivityListAdapter(Context context, SalleLocationManager locations, int tag) {
        this.context = context;
        this.locations = locations;
        this.tag = tag;
    }


    @Override
    public int getCount() {
        return locations.size(tag);
    }

    @Override
    public Object getItem(int position) {
        return locations.getLocations(tag).get(position);
    }

    @Override
    public long getItemId(int position) {
        return locations.getLocations(tag).get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(inflater!=null)
                view = inflater.inflate(R.layout.activity_main_tabbed_list_view_item,parent,false);
            else return null;
        }
        SalleLocation location = locations.getLocations(tag).get(position);
        TextView name = view.findViewById(R.id.main_activity_list_view_item_name);
        TextView address = view.findViewById(R.id.main_activity_list_view_item_location);
        TextView type[] = new TextView[6];
        type[0] = view.findViewById(R.id.main_activity_list_view_item_infantil);
        type[1] = view.findViewById(R.id.main_activity_list_view_item_primaria);
        type[2] = view.findViewById(R.id.main_activity_list_view_item_eso);
        type[3] = view.findViewById(R.id.main_activity_list_view_item_bachillerato);
        type[4] = view.findViewById(R.id.main_activity_list_view_item_fp);
        type[5] = view.findViewById(R.id.main_activity_list_view_item_universidad);

        for(int i=0;i<type.length;i++){
            type[i].setVisibility(location.getType()[i]?View.VISIBLE:View.GONE);
        }
        name.setText(location.getName());
        address.setText(location.getAddress());

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, DetallesActivity.class);
        intent.putExtra(MainActivity.SELECTED_SCHOOL_TAG,tag);
        intent.putExtra(MainActivity.SELECTED_SCHOOL_POSITION,position);
        context.startActivity(intent);
    }
}
