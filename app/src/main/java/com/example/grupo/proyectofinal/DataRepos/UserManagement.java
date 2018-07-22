package com.example.grupo.proyectofinal.DataRepos;

/**
 * Created by bn32w on 22/07/18.
 */

public interface UserManagement {
    boolean isUser(String username, String password);
    boolean createUser(String username,String name, String lastName,String password);
}
