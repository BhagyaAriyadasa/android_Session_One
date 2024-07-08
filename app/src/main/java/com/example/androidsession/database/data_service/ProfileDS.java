package com.example.androidsession.database.data_service;

public class ProfileDS {

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
