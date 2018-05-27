package com.example.grupo.proyectofinal.Adapter;

import com.example.grupo.proyectofinal.Model.SalleLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bn32w on 23/05/18.
 */

public class MapAdapter implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    public interface IntUpdater{
        void updateInterface(SalleLocation location, int position);
    }
    private List<SalleLocation> locationList;
    private ArrayList<Marker> markers;
    private IntUpdater updater;

    public MapAdapter(List<SalleLocation> locationList,IntUpdater updater){
        this.locationList=locationList;
        markers = new ArrayList<>();
        this.updater = updater;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        SalleLocation loc=locationList.get(0);
        googleMap.setOnMarkerClickListener(this);
        for(SalleLocation location:locationList){
            MarkerOptions options = new MarkerOptions();
            if(location.getLocation()==null){
                continue;
            }
            options.position(location.getLocation());
            options.title(location.getName());
            options.snippet(location.getAddress());
            options.icon(BitmapDescriptorFactory.defaultMarker(location.getProminentColor()));
            markers.add(googleMap.addMarker(options));
            if(location.getName().contains("Manr")){
                loc = location;
            }
        }
        googleMap.setMinZoomPreference(7.5f);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc.getLocation()));

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        for(int index=0;index<locationList.size();index++){
            SalleLocation loc = locationList.get(index);
            if(loc.getName().equals(marker.getTitle())){
                updater.updateInterface(loc,index);
                return false;
            }
        }
        return false;
    }
}
