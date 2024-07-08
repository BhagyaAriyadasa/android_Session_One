package com.example.androidsession.database.data_service;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

import com.example.androidsession.database.DataBaseHelper;
import com.example.androidsession.database.table_entity.ProfileEntity;

public class ProfileDS {

    DataBaseHelper dataBaseHelper;

    public ProfileDS(Context context){
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    public boolean createOrUpdate(ProfileEntity object) {
        boolean action = false;

        try{
            String sql = " insert or replace into " + tableName
                    + "("
                    + col_Name + ","
                    + col_Age + ","
                    + col_TeamUid + ","
                    + col_CityUid + ","
                    + col_IsActive + ","
                    + col_Salary + ")"
                    + " values (?,?,?,?,?,?)";

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

    private  void insertStatement(SQLiteStatement statement, ProfileEntity object){
        statement.bindString(1, object.getName());
        statement.bindLong(2, object.getAge());
        statement.bindLong(3, object.getTeamUid());
        statement.bindLong(4, object.getCityUid());
        statement.bindString(5, object.isIsActive() + "");
        statement.bindDouble(6, object.getSalary());
        statement.execute();
    }

    public static String tableName = "Profile";
    public static String col_UID = "UID";
    public static String col_Name = "Name";
    public static String  col_Age = "Age";
    public static String col_TeamUid = "TeamUid";
    public static String col_CityUid = "CityUid";
    public static String col_IsActive = "IsActive";
    public static String col_Salary = "Salary";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_Name + " text ," +
            col_Age + " integer ," +
            col_TeamUid + " integer ," +
            col_CityUid + " integer ," +
            col_IsActive + " boolean default 'false' ," +
            col_Salary + " double " +
            ");";
}
