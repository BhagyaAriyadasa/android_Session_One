package com.example.androidsession.database.data_service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.androidsession.Utils;
import com.example.androidsession.database.DataBaseHelper;
import com.example.androidsession.database.query_entity.LoginValidationEntity;
import com.example.androidsession.database.table_entity.CityEntity;
import com.example.androidsession.database.table_entity.LoginEntity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LoginDS {

    DataBaseHelper dataBaseHelper;

    public LoginDS(Context context){
        dataBaseHelper = DataBaseHelper.getInstance(context);
    }

    public boolean createOrUpdate(LoginEntity object){
        boolean action = false;

        try{
            String sql = "insert or replace into " + tableName
                    + "("
                    + col_UserName + ","
                    + col_Password + ","
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

    private void insertStatement(SQLiteStatement statement, LoginEntity object) {
        statement.bindString(1, object.getUserName());
        statement.bindString(2, object.getPassword());
//        statement.bindString(3, object.isIsActive()+"");
        statement.bindLong(3, object.isIsActive() ? 1 : 0);
        statement.execute();
    }

    public int getLastInsertedUid() {
        int lastInsertId = 0;
        try {
            String sql = "SELECT last_insert_rowid()";
            SQLiteStatement statement = dataBaseHelper.getDB().compileStatement(sql);
            lastInsertId = (int) statement.simpleQueryForLong();
        } catch (Exception e) {
            System.err.println(e);
        }
        return lastInsertId;
    }

    @SuppressLint("Range")
    public boolean getIsActiveval(String username, String password) throws JSONException {
        boolean isActive = false;
        String sql = "SELECT " + col_IsActive + " FROM " + tableName + " WHERE " + col_UserName + " = ? AND " + col_Password + " = ?";

        Cursor cursor = null;
        try {
            dataBaseHelper.getDB().beginTransaction();
            cursor = dataBaseHelper.getDB().rawQuery(sql, new String[]{username, password});

            if (cursor != null && cursor.moveToFirst()) {
                isActive = cursor.getInt(cursor.getColumnIndex(col_IsActive)) > 0;
            }
            dataBaseHelper.getDB().setTransactionSuccessful();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dataBaseHelper.getDB().endTransaction();
        }

        return isActive;
    }


    public List<LoginValidationEntity> getIsActive(String username , String password) throws JSONException {
        List<LoginValidationEntity> list = new ArrayList<>();
        String sql = "SELECT " + col_IsActive + " FROM " + tableName + " WHERE " + col_UserName + " = '" + username + "' AND " + col_Password + " = '" + password + "'";
        dataBaseHelper.getDB().beginTransaction();
        JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));
        for (int i = 0; i < array.length(); i++) {
            list.add(new Gson().fromJson(array.getJSONObject(i).toString(), LoginValidationEntity.class));
        }
        return list;
    }

    public static String tableName = "Login";
    public static String col_UID = "UID";
    public static String col_UserName = "UserName";
    public static String col_Password = "Password";
    public static String col_IsActive = "IsActive";

    public static String create = "create table if not exists " +
            tableName + "(" +
            col_UID + " integer primary key autoincrement ," +
            col_UserName + " text ," +
            col_Password + " text ," +
            col_IsActive + " integer default 0 " +
            ");";

}
