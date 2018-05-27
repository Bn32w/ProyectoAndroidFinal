package com.example.grupo.proyectofinal.Model;

import android.content.res.Resources;
import android.os.Parcelable;

import com.example.grupo.proyectofinal.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by bn32w on 15/05/18.
 */

public class SalleLocation implements Serializable{
    private final int id;
    private final String name;
    private final String address;
    private LatLng location;
    private final String province;
    private final boolean[] type;
    private final String description;

    public static final Comparator<SalleLocation> sortBarcelona = new Comparator<SalleLocation>() {
        @Override
        public int compare(SalleLocation o1, SalleLocation o2) {
            if((!o1.getProvince().toLowerCase().startsWith("b")&&!o2.getProvince().toLowerCase().startsWith("b"))
                    || (o1.getProvince().toLowerCase().startsWith("b")&&o2.getProvince().toLowerCase().startsWith("b"))){
                return o1.getId()-o2.getId();
            }
            if(o1.getProvince().toLowerCase().startsWith("b")){
                return -1;
            }
            return 1;
        }
    };

    public static final Comparator<SalleLocation> sortTarragona = new Comparator<SalleLocation>() {
        @Override
        public int compare(SalleLocation o1, SalleLocation o2) {

            if((!o1.getProvince().toLowerCase().startsWith("t")&&!o2.getProvince().toLowerCase().startsWith("t"))
                    || (o1.getProvince().toLowerCase().startsWith("t")&&o2.getProvince().toLowerCase().startsWith("t"))){
                return o1.getId()-o2.getId();
            }
            if(o1.getProvince().toLowerCase().startsWith("t")){
                return -1;
            }
            return 1;
        }
    };

    public static final Comparator<SalleLocation> sortGirona = new Comparator<SalleLocation>() {
        @Override
        public int compare(SalleLocation o1, SalleLocation o2) {
            if((!o1.getProvince().toLowerCase().startsWith("g")&&!o2.getProvince().toLowerCase().startsWith("g"))
                    || (o1.getProvince().toLowerCase().startsWith("g")&&o2.getProvince().toLowerCase().startsWith("g"))){
                return o1.getId()-o2.getId();
            }
            if(o1.getProvince().toLowerCase().startsWith("g")){
                return -1;
            }
            return 1;
        }
    };

    public static final Comparator<SalleLocation> sortLleida = new Comparator<SalleLocation>() {
        @Override
        public int compare(SalleLocation o1, SalleLocation o2) {
            if((!o1.getProvince().toLowerCase().startsWith("l")&&!o2.getProvince().toLowerCase().startsWith("l"))
                    || (o1.getProvince().toLowerCase().startsWith("l")&&o2.getProvince().toLowerCase().startsWith("l"))){
                return o1.getId()-o2.getId();
            }
            if(o1.getProvince().toLowerCase().startsWith("l")){
                return -1;
            }
            return 1;
        }
    };



    public SalleLocation(int id, String name, String address, String province,boolean[] type, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.province = province;
        this.type = type;
        this.description=description;
        location=null;
    }

    void setLocation(LatLng location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getLocation() {
        return location;
    }


    boolean needsLoadingLocation(){
        return location==null;
    }

    public String getProvince() {
        return province;
    }

    public boolean[] getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean escuela() {
        return type[0]||type[1]||type[2];
    }

    public boolean otro() {
         return type[3]||type[4]||type[5];
    }

    public float getProminentColor() {
        if(type[5]){
            return BitmapDescriptorFactory.HUE_AZURE;
        }
        if(type[4]){
            return BitmapDescriptorFactory.HUE_GREEN;
        }
        if(type[3]){
            return BitmapDescriptorFactory.HUE_RED;
        }
        if(type[2]){
            return BitmapDescriptorFactory.HUE_ORANGE;
        }
        if(type[1]){
            return  BitmapDescriptorFactory.HUE_YELLOW;
        }
        return BitmapDescriptorFactory.HUE_MAGENTA;
    }
}
