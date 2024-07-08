package com.example.androidsession.database.data_service;

import android.content.Context;

import com.example.androidsession.Utils;
import com.example.androidsession.database.DataBaseHelper;
import com.example.androidsession.database.table_entity.CityEntity;
import com.example.androidsession.database.table_entity.TeamEntity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TeamDS {

    DataBaseHelper dataBaseHelper;

    public TeamDS(Context context){
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    public List<TeamEntity> getTeamUIDAndName() throws JSONException {
        List<TeamEntity> list = new ArrayList<>();
        String sql = "select " + col_UID + ", " + col_Name + " from " + tableName;
        dataBaseHelper.getDB().beginTransaction();
        JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));
        for (int i = 0; i < array.length(); i++) {
            list.add(new Gson().fromJson(array.getJSONObject(i).toString(), TeamEntity.class));
        }
        dataBaseHelper.getDB().endTransaction();
        return list;
    }

    public static String tableName = "Team";
    public static String col_UID = "UID";
    public static String col_Name = "Name";
    public static String col_IsActive = "IsActive";
    public static String col_MaxCount = "MaxCount";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_Name + " text ," +
            col_MaxCount + " integer ," +
            col_IsActive + " boolean default 'false' " +
            ");";
}
