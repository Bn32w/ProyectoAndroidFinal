package com.example.grupo.proyectofinal.DataRepos;

import com.example.grupo.proyectofinal.Model.SalleLocation;

import java.util.List;

/**
 * Created by bn32w on 15/05/18.
 */

public interface LocationsRepo {
    List<SalleLocation> getLocationFromRepo();

    boolean putLocationIntoRepo(SalleLocation location);

    boolean deleteLocationFromRepo(int id);
}
