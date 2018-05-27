package com.example.grupo.proyectofinal.Model;

import android.content.Context;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.example.grupo.proyectofinal.DataRepos.WebImpl.WebserviceLocationRepo;
import com.example.grupo.proyectofinal.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by bn32w on 15/05/18.
 */

public class SalleLocationManager {

    private static SalleLocationManager instance;

    public synchronized static SalleLocationManager getInstance() {
        if (instance == null) {
            instance = new SalleLocationManager();
        }
        return instance;
    }

    public interface Viewer {
        void updateView();

        Context getContext();

        void startProgressBar();

        void stopProgressBar();
    }

    private static final String FILL_LOCATIONS = "fillLocations";

    private ArrayList<SalleLocation> locations, otros, escuelas;
    private Locations asyncLocationLoader;
    private boolean happened;
    private int size;


    private SalleLocationManager() {
        this.locations = new ArrayList<>();
        otros = null;
        escuelas = null;
        happened = false;
    }


    public void fillLocationsFromWebservice(Viewer view, boolean force) {
        if (!happened || force) {
            locations.clear();
            asyncLocationLoader = new Locations(locations, view);
            asyncLocationLoader.execute(FILL_LOCATIONS);
            happened = true;
        }
    }

    public void addLocation(SalleLocation location, Context context) {
        //TODO addLocationToWebService
        //TODO? getLocations
    }

    public List<SalleLocation> getLocations(int tag) {
        switch (tag) {
            case R.string.escuelas:
                return getEscuelasLocation();
            case R.string.otros:
                return getOtrosLocation();
            default:
                return locations;
        }
    }

    private List<SalleLocation> getOtrosLocation() {
        if (otros == null || otros.isEmpty()) {
            otros = new ArrayList<>();
            for(int i=0;i<size;i++){
                SalleLocation salleLoc = locations.get(i);
                if (salleLoc.otro()) {
                    otros.add(salleLoc);
                }
            }
        }
        return otros;
    }

    private List<SalleLocation> getEscuelasLocation() {
        if (escuelas == null || escuelas.isEmpty()) {
            escuelas = new ArrayList<>();
            for(int i=0;i<size;i++){
                SalleLocation salleLoc = locations.get(i);
                if (salleLoc.escuela()) {
                    escuelas.add(salleLoc);
                }
            }
        }
        return escuelas;
    }

    public int size(int tag){
        switch (tag) {
            case R.string.escuelas:
                return escuelas==null?0:escuelas.size();
            case R.string.otros:
                return otros==null?0:otros.size();
            default:
                return size;
        }
    }

    public void sortArray(Comparator<SalleLocation> comparator,String type){
        Collections.sort(locations,comparator);
        for(size=0;size<locations.size();size++){
            if(!locations.get(size).getProvince().equalsIgnoreCase(type)){
                break;
            }
        }
        escuelas=null;
        otros=null;
        getEscuelasLocation();
        getOtrosLocation();
    }

    private static class Locations extends AsyncTask<String,Void,Boolean>{
        private List<SalleLocation> locations,currentLocations;
        private Viewer view;

        private Locations(List<SalleLocation> locations, Viewer view){
            this.locations=locations;
            this.view=view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.startProgressBar();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(strings[0].equals(FILL_LOCATIONS)){
                currentLocations = new WebserviceLocationRepo().getLocationFromRepo();
                return currentLocations!=null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean salleLocations) {
            super.onPostExecute(salleLocations);
            if(!salleLocations){
                return;
            }
            locations.addAll(currentLocations);
            if(locations.get(0).getLocation()==null){
                new Geolocate(view).execute(locations);
            }
        }

    }

    private static class Geolocate extends AsyncTask<List<SalleLocation>,Void,Void>{
        private Viewer viewer;

        private Geolocate(Viewer viewer){
            this.viewer = viewer;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(List<SalleLocation>[] lists) {
            //https://stackoverflow.com/questions/30740306/how-to-get-geographical-coordinates-in-google-maps-api-v2-android-using-an-strin
            List<SalleLocation> locations = lists[0];
            Geocoder coder = new Geocoder(viewer.getContext(), Locale.getDefault());
            for(Iterator<SalleLocation> iterator = locations.iterator();iterator.hasNext();){
                SalleLocation location = iterator.next();
                if(location.needsLoadingLocation()){
                    String address = location.getAddress().replace(","," ");
                    try{
                        List<Address> addresses = coder.getFromLocationName(address,1);
                        if(addresses.size()>0){
                            location.setLocation(new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude()));
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewer.updateView();
            viewer.stopProgressBar();
        }
    }
}
