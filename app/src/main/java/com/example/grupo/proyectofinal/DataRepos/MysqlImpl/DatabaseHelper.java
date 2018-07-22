package com.example.grupo.proyectofinal.DataRepos.MysqlImpl;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.example.grupo.proyectofinal.R;


/**
 * Created by Eduard on 04/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String name = "users_db";
    private static final int version = 3;
    private static SQLiteDatabase.CursorFactory factory;

    // Instancia del patron de diseño SINGLETON
    private static DatabaseHelper instance;

    private Context context;

    private DatabaseHelper(Context context,
                           String name,
                           SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context, name, factory, version);
        }
        instance.context = context;

        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear DB.
        executeSQLScript(db, R.raw.db_creation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Destruir DB.
        executeSQLScript(db, R.raw.db_removal);
        // Crear DB.
        executeSQLScript(db, R.raw.db_creation);
    }

    private void executeSQLScript(SQLiteDatabase database, int scriptFile) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        InputStream inputStream = null;

        try{
            inputStream = context.getResources().openRawResource(scriptFile);
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            String[] createScript = outputStream.toString().split(";");
            for (int i = 0; i < createScript.length; i++) {
                String sqlStatement = createScript[i].trim();
                // TODO You may want to parse out comments here
                if (sqlStatement.length() > 0) {
                    database.execSQL(sqlStatement + ";");
                }
            }
        } catch (IOException e) {
            // TODO Handle Script Failed to Load
        } catch (SQLException e) {
            // TODO Handle Script Failed to Execute
        }
    }


}
