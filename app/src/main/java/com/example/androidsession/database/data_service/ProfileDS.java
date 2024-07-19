package com.example.androidsession.database.data_service;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

import com.example.androidsession.Utils;
import com.example.androidsession.database.DataBaseHelper;
import com.example.androidsession.database.table_entity.ProfileEntity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
                    + col_Salary + ","
                    + col_LoginUid + ")"
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

    public List<ProfileEntity> getAll() throws JSONException {
        List<ProfileEntity> list = new ArrayList<>();
        String sql = "select * from " + tableName;
        dataBaseHelper.getDB().beginTransaction();
        JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));

        for(int i = 0; i < array.length(); i++){
            list.add(new Gson().fromJson(array.getJSONObject(i).toString(),ProfileEntity.class));
        }
        return  list;
    }

    private  void insertStatement(SQLiteStatement statement, ProfileEntity object){
        statement.bindString(1, object.getName());
        statement.bindLong(2, object.getAge());
        statement.bindLong(3, object.getTeamUid());
        statement.bindLong(4, object.getCityUid());
        statement.bindDouble(5, object.getSalary());
        statement.bindLong(6, object.getLoginUid());
        statement.execute();
    }

    public static String tableName = "Profile";
    public static String col_UID = "UID";
    public static String col_Name = "Name";
    public static String  col_Age = "Age";
    public static String col_TeamUid = "TeamUid";
    public static String col_CityUid = "CityUid";
    public static String col_Salary = "Salary";
    public static String col_LoginUid = "LoginUid";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_Name + " text ," +
            col_Age + " integer ," +
            col_TeamUid + " integer ," +
            col_CityUid + " integer ," +
            col_Salary + " double ," +
            col_LoginUid + " int " +
            ");";

    public static String addLoginUID = "ALTER TABLE "+tableName+" ADD COLUMN "+col_LoginUid+" INTEGER;";

    public static String renameTable = "ALTER TABLE "+tableName+" RENAME TO "+tableName+"old";

    public static String createTable = "CREATE TABLE IF NOT EXISTS " +tableName+" ("+ col_UID+" INTEGER ," + col_Name+" TEXT ,"+ col_Age+" INTEGER ,"+ col_TeamUid+" INTEGER ," +col_CityUid+" INTEGER ,"+ col_Salary+" DOUBLE ," +col_LoginUid+" INTEGER " +") ";

    public static String insertData = "INSERT INTO " +tableName+ " SELECT " + col_UID +" ," + col_Name + " ," + col_Age + " ," + col_TeamUid + " ," + col_CityUid + " ," + col_Salary + " ," + col_LoginUid + " FROM " + tableName+"old";

    public static String dropTable = "DROP TABLE " + tableName+"old";

}
