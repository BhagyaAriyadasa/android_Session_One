package com.example.androidsession.database.data_service;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

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

    public boolean createOrUpdate(List<TeamEntity> list){
        boolean action = false;

        try{
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_UID + ","
                    + col_IsActive + ","
                    + col_MaxCount + ","
                    + col_Name + ")"
                    + " values (?,?,?,?)";

            dataBaseHelper.getDB().beginTransaction();

            SQLiteStatement statement = dataBaseHelper.getDB().compileStatement(sql);
            list.forEach(teamEntity -> insertStatement(statement,teamEntity));
            dataBaseHelper.getDB().setTransactionSuccessful();
            action = true;
        }catch (Exception e){
            System.err.println(e);
        }finally {
            dataBaseHelper.getDB().endTransaction();
        }
        return action;
    }

    private void insertStatement(SQLiteStatement statement, TeamEntity object) {
        statement.bindLong(1, object.getUID());
        statement.bindString(2, object.isIsActive()+"");
        statement.bindLong(3, object.getMaxCount());
        statement.bindString(4, object.getName());
        statement.execute();
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

    public void insertTeams() {
        List<TeamEntity> teams = new ArrayList<>();
        teams.add(new TeamEntity(1, true, 10, "Team 1"));
        teams.add(new TeamEntity(2, true, 10, "Team 2"));
        teams.add(new TeamEntity(3, true, 10, "Team 3"));
        teams.add(new TeamEntity(4, true, 10, "Team 4"));
        teams.add(new TeamEntity(5, true, 10, "Team 5"));

        createOrUpdate(teams);
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
