package com.home.kt.noteddictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KT on 3/8/2016.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //Database detail
    static final String db="dbDict.DB";
    static final int dbversion=1;

    //Table detail
    public static final String tb="tbDict";
    public static final String col_id ="_id";
    public static final String col_word ="w";
    public static final String col_definition ="d";

    //Statement creates table
    private static final String createTable="create table "+ tb +"("
            + col_id +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "

            + col_word +" TEXT NOT NULL, "
            + col_definition +" TEXT NOT NULL);";

    public MySQLiteOpenHelper(Context context) {
        super(context, db, null, dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tb);
    }
}