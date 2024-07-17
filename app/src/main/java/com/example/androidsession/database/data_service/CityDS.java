package com.example.androidsession.database.data_service;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

import com.example.androidsession.Utils;
import com.example.androidsession.database.DataBaseHelper;
import com.example.androidsession.database.table_entity.CityEntity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CityDS {

    DataBaseHelper dataBaseHelper;

    public CityDS(Context context){
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    public boolean createOrUpdate(CityEntity object){
        boolean action = false;

        try{
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_UID + ","
                    + col_Name + ","
                    + col_IsActive + ")"
                    + " values (?,?,?)";

            dataBaseHelper.getDB().beginTransaction();

            SQLiteStatement statement = dataBaseHelper.getDB().compileStatement(sql);
            insertStatement(statement,object);
            dataBaseHelper.getDB().setTransactionSuccessful();
            action = true;
        }catch (Exception e){
            System.err.println(e);
        }finally {
            dataBaseHelper.getDB().endTransaction();
        }
        return action;
    }

    public boolean createOrUpdate(List<CityEntity> list){
        boolean action = false;

        try{
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_UID + ","
                    + col_Name + ","
                    + col_IsActive + ")"
                    + " values (?,?,?)";

            dataBaseHelper.getDB().beginTransaction();

            SQLiteStatement statement = dataBaseHelper.getDB().compileStatement(sql);
            list.forEach(cityEntity -> insertStatement(statement,cityEntity));
            dataBaseHelper.getDB().setTransactionSuccessful();
            action = true;
        }catch (Exception e){
            System.err.println(e);
        }finally {
            dataBaseHelper.getDB().endTransaction();
        }
        return action;
    }

    private void insertStatement(SQLiteStatement statement, CityEntity object) {
        statement.bindLong(1, object.getUID());
        statement.bindString(2, object.getName());
        statement.bindString(3, object.isIsActive()+"");
        statement.execute();
    }

//    public List<CityEntity> getAll() throws JSONException {
//        List<CityEntity> list = new ArrayList<>();
//        String sql = "select * from " + tableName;
//        dataBaseHelper.getDB().beginTransaction();
//        JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));
//        for (int i = 0; i < array.length(); i++) {
//            list.add(new Gson().fromJson(array.getJSONObject(i).toString(), CityEntity.class));
//        }
//        return list;
//    }

    public List<CityEntity> getCityUIDAndName() throws JSONException {
        List<CityEntity> list = new ArrayList<>();
        String sql = "select " + col_UID + ", " + col_Name + " from " + tableName;
        dataBaseHelper.getDB().beginTransaction();
        JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));
        for (int i = 0; i < array.length(); i++) {
            list.add(new Gson().fromJson(array.getJSONObject(i).toString(), CityEntity.class));
        }
        dataBaseHelper.getDB().endTransaction();
        return list;
    }

    public void insertCities() {
        List<CityEntity> cities = new ArrayList<>();
        cities.add(new CityEntity(1, "City1", true));
        cities.add(new CityEntity(2, "City2", false));
        cities.add(new CityEntity(3, "City3", true));
        cities.add(new CityEntity(4, "City4", false));
        cities.add(new CityEntity(5, "City5", true));

        createOrUpdate(cities);
    }


    public static String tableName = "City";
    public static String col_UID = "UID";
    public static String col_Name = "Name";
    public static String col_IsActive = "IsActive";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_Name + " text ," +
            col_IsActive + " boolean default 'false' " +
            ");";
}
