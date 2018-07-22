package com.example.grupo.proyectofinal.DataRepos.MysqlImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.grupo.proyectofinal.DataRepos.UserManagement;

/**
 * Created by bn32w on 22/07/18.
 */

public class MysqlUserImpl implements UserManagement {
    private static final String table = "users";
    private static final String username = "username";
    private static final String name = "name";
    private static final String surname = "surname";
    private static final String password = "password";

    private final DatabaseHelper helper;


    public MysqlUserImpl(Context context){
        helper = DatabaseHelper.getInstance(context);
    }

    @Override
    public boolean isUser(String username, String password) {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] columnas = {MysqlUserImpl.username};
        String selection = "WHERE "+MysqlUserImpl.username+"='?' and "+MysqlUserImpl.password+"='?'";
        String selectonArgs[] = {username,password};
        Cursor cursor = database.query(table,columnas,selection,selectonArgs,null,null,null);
        boolean data = cursor.moveToNext();
        cursor.close();
        return data;
    }

    @Override
    public boolean createUser(String username, String name, String lastName, String password) {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] columnas = {MysqlUserImpl.username};
        String selection = "WHERE "+MysqlUserImpl.username+"='?'";
        String selectonArgs[] = {username};
        Cursor cursor = database.query(table,columnas,selection,selectonArgs,null,null,null);
        if(cursor.moveToNext()){
            cursor.close();
            return false;
        }
        database.close();
        cursor.close();

        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MysqlUserImpl.username,username);
        values.put(MysqlUserImpl.name,name);
        values.put(MysqlUserImpl.surname,lastName);
        values.put(MysqlUserImpl.password,password);

        database.insert(table,null,values);
        return true;
    }
}
