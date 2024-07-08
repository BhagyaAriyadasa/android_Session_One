package com.example.androidsession.database.data_service;

public class TeamDS {
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
