package com.example.androidsession.database.data_service;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
    public static int lastInsertedId = -1;

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
            lastInsertedId = (int) dataBaseHelper.getDB().compileStatement("SELECT last_insert_rowid()").simpleQueryForLong();
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
        statement.bindString(3, object.isIsActive()+"");
        statement.execute();
    }

    public int getLastInsertedUid() {
        int lastInsertId = 0;
        SQLiteDatabase db = dataBaseHelper.getDB();
        if (db != null) {
            SQLiteStatement statement = null;
            try {
                String sql = "SELECT last_insert_rowid()";
                statement = db.compileStatement(sql);
                lastInsertId = (int) statement.simpleQueryForLong();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        }
        return lastInsertId;
    }


    @SuppressLint("Range")
    public boolean getIsActive(String username, String password) throws JSONException {
        boolean isActive = false;
        String sql = "SELECT " + col_IsActive + " FROM " + tableName + " WHERE " + col_UserName + " = ? AND " + col_Password + " = ?";

        Cursor cursor = null;
        try {
            dataBaseHelper.getDB().beginTransaction();
            cursor = dataBaseHelper.getDB().rawQuery(sql, new String[]{username, password});

            if (cursor != null && cursor.moveToFirst()) {
                isActive = "true".equals(cursor.getString(cursor.getColumnIndex(col_IsActive)));
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


//    public List<LoginValidationEntity> getIsActive(String username , String password) throws JSONException {
//        List<LoginValidationEntity> list = new ArrayList<>();
//        String sql = "SELECT " + col_IsActive + " FROM " + tableName + " WHERE " + col_UserName + " = '" + username + "' AND " + col_Password + " = '" + password + "'";
//        dataBaseHelper.getDB().beginTransaction();
//        JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));
//        for (int i = 0; i < array.length(); i++) {
//            list.add(new Gson().fromJson(array.getJSONObject(i).toString(), LoginValidationEntity.class));
//        }
//        return list;
//    }

    public List<LoginEntity> getActiveUsers() throws JSONException {
        List<LoginEntity> list = new ArrayList<>();
        try{
           String sql = "select * from " + tableName + " where " + col_IsActive + " = 'true'";
           dataBaseHelper.getDB().beginTransaction();
           JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));
           for (int i = 0; i < array.length(); i++) {
               list.add(new Gson().fromJson(array.getJSONObject(i).toString(), LoginEntity.class));
           }
           dataBaseHelper.getDB().endTransaction();
       }catch (Exception e){
           System.err.println(e);
       }
        return list;
    }

    public List<LoginEntity> getInactiveUsers() throws JSONException {
        List<LoginEntity> list = new ArrayList<>();
        try{
            String sql = "select * from " + tableName + " where " + col_IsActive + " = 'false'";
            dataBaseHelper.getDB().beginTransaction();
            JSONArray array = Utils.getArray(dataBaseHelper.getDB().rawQuery(sql, null));
            for (int i = 0; i < array.length(); i++) {
                list.add(new Gson().fromJson(array.getJSONObject(i).toString(), LoginEntity.class));
            }
            dataBaseHelper.getDB().endTransaction();
        }catch (Exception e){
            System.err.println(e);
        }
        return list;
    }

    public boolean updateUser(LoginEntity user) {
        boolean action = false;
        try {
            dataBaseHelper.getDB().beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_UserName, user.getUserName());
            contentValues.put(col_Password, user.getPassword());
            contentValues.put(col_IsActive, user.isIsActive() ? "false" : "true");

            String whereClause = col_UserName + " = ?";
            String[] whereArgs = {user.getUserName()};

            dataBaseHelper.getDB().update(tableName, contentValues, whereClause, whereArgs);
            dataBaseHelper.getDB().setTransactionSuccessful();
            action = true;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            dataBaseHelper.getDB().endTransaction();
        }
        return action;
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
