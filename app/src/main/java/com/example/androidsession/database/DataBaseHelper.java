package com.example.androidsession.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.data_service.ProfileDS;
import com.example.androidsession.database.data_service.TeamDS;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int databaseVersion = 9;
    private static final String databaseName = "MyDatabase";

    //to use outside of the class
    private static DataBaseHelper instance;
    private static SQLiteDatabase database;

    public DataBaseHelper(@Nullable Context context) {
        super(context, databaseName, null , databaseVersion);
    }

    //may be take a long time because set as synchronized
    public static synchronized DataBaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    public synchronized SQLiteDatabase getDB() {
        if(database == null || !database.isOpen()) {
            database = instance.getWritableDatabase();
            database.disableWriteAheadLogging();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(ProfileDS.create);
        }catch (Exception e){
            System.err.println(e);
        }

        try{
            db.execSQL(TeamDS.create);
        }catch (Exception e){
            System.err.println(e);
        }

        try{
            db.execSQL(CityDS.create);
        }catch (Exception e){
            System.err.println(e);
        }

        try {
            db.execSQL(LoginDS.create);
        }catch (Exception e){
            System.err.println(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2){
            db.execSQL("ALTER TABLE Profile ADD COLUMN loginUID INTEGER;");
//            db.execSQL("CREATE TABLE Profile_temp (UID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Age INTEGER, TeamUid INTEGER, CityUid INTEGER, Salary double, loginUID INTEGER);");
            db.execSQL("CREATE TABLE Profile_temp (UID INTEGER PRIMARY KEY AUTOINCREMENT);");

//            db.execSQL("INSERT INTO Profile_temp (Name, Age, TeamUid, CityUid) SELECT  Name, Age, TeamUid, CityUid FROM Profile;");
//
//            db.execSQL("DROP TABLE Profile;");
//
//            db.execSQL("ALTER TABLE Profile_temp RENAME TO Profile;");
        }
        onCreate(db);
    }
}
