package com.example.grupo.proyectofinal.Adapter;

import com.example.grupo.proyectofinal.Model.SalleLocation;
import com.example.grupo.proyectofinal.Model.SalleLocationManager;
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
    private boolean mapReady=false;
    private int toClick=-1;
    private GoogleMap map;

    public void clickMarker(int intExtra) {
        if(mapReady){
            doClick(intExtra);
        }else {
            toClick=intExtra;
        }
    }

    private void doClick(int intExtra) {
        for(int i=0;i<markers.size();i++){
            if(locationList.get(intExtra).getName().equals(markers.get(i).getTitle())){
                Marker marker = markers.get(i);
                marker.showInfoWindow();
                map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()), 250, null);
                updater.updateInterface(locationList.get(intExtra),intExtra);
            }
        }
    }

    public interface IntUpdater{
        void updateInterface(SalleLocation location, int position);
    }
    private List<SalleLocation> locationList;
    private ArrayList<Marker> markers;
    private IntUpdater updater;

    public MapAdapter(IntUpdater updater, int locationState){
        this.locationList= SalleLocationManager.getInstance().getLocations(locationState);
        markers = new ArrayList<>();
        this.updater = updater;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        createMarkers(googleMap);
        mapReady=true;
        if(toClick>=0){
            doClick(toClick);
        }
    }

    public void changeTargets(int tag){
        if(map!=null){
            locationList = SalleLocationManager.getInstance().getLocations(tag);
            createMarkers(map);
        }
    }

    private void createMarkers(GoogleMap googleMap) {
        googleMap.clear();
        markers.clear();
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
