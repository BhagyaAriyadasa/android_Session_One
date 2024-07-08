package com.example.androidsession.database.data_service;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;

import com.example.androidsession.database.DataBaseHelper;
import com.example.androidsession.database.table_entity.LoginEntity;

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
        statement.bindString(3, object.isIsActive()+"");
        statement.execute();
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
            col_IsActive + " boolean default 'false' " +
            ");";

}
