package com.example.grupo.proyectofinal.DataRepos.WebImpl;

import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.example.grupo.proyectofinal.DataRepos.LocationsRepo;
import com.example.grupo.proyectofinal.Model.SalleLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by bn32w on 15/05/18.
 */

public class WebserviceLocationRepo implements LocationsRepo {

    private static class HTMLHelper{
        private static JSONObject getObjectFromHttp(String params,String method){
            //https://stackoverflow.com/questions/4205980/java-sending-http-parameters-via-post-method-easily
            try {
                StringBuilder request = new StringBuilder().append("https://testapi-pprog2.azurewebsites.net/api/schools.php?");
                URL url = new URL(request.toString());
                HttpURLConnection connection;
                switch (method){
                    case "GET":
                        request.append(params);
                        connection = buildConnection(method, request);
                        connection.connect();
                        int status = connection.getResponseCode();
                        switch (status){
                            case HttpURLConnection.HTTP_OK:
                            case HttpURLConnection.HTTP_CREATED:
                                return getJsonObject(connection);
                        }
                        break;
                    case "POST":
                        byte[] postData = params.getBytes(StandardCharsets.UTF_8);
                        int postDataLenght = postData.length;

                        connection = buildConnection(method, request);
                        connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                        connection.setRequestProperty( "charset", "utf-8");
                        connection.setRequestProperty( "Content-Length", Integer.toString( postDataLenght ));

                        connection.setDoOutput(true);
                        connection.getOutputStream().write(postData);

                        return getJsonObject(connection);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @NonNull
        private static JSONObject getJsonObject(HttpURLConnection connection) throws IOException, JSONException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                builder.append(line).append(System.lineSeparator());
            }
            reader.close();
            return new JSONObject(builder.toString());
        }

        private static HttpURLConnection buildConnection(String method, StringBuilder request) throws IOException {
            URL url;
            HttpURLConnection connection;
            url = new URL(request.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setUseCaches(false);

            return connection;
        }
    }

    @Override
    public List<SalleLocation> getLocationFromRepo() {
        String params = "method=getSchools";
        JSONObject object = HTMLHelper.getObjectFromHttp(params,"GET");
        List<SalleLocation> list=new ArrayList<>();
        try {
            if(object==null || object.getInt("res")!=1){
                return null;
            }
            JSONArray data = object.getJSONArray("msg");
            for(int i=0;i<data.length();i++){
                JSONObject jsonObject = data.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("schoolName");
                String address = jsonObject.getString("schoolAddress");
                String province = jsonObject.getString("province");
                String description = jsonObject.getString("description");
                boolean type[] = {jsonObject.getInt("isInfantil")==1, jsonObject.getInt("isPrimaria")==1, jsonObject.getInt("isEso")==1
                                    ,jsonObject.getInt("isBatxillerat")==1,jsonObject.getInt("isFP")==1,jsonObject.getInt("isUniversitat")==1};
                SalleLocation location = new SalleLocation(id,name,address,province,type,description);
                list.add(location);
            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean putLocationIntoRepo(SalleLocation location) {
        return false;
    }

    @Override
    public boolean deleteLocationFromRepo(int id) {
        return false;
    }
}
