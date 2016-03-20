package com.home.kt.noteddictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by KT on 3/8/2016.
 */
public class MyModify {
    private MySQLiteOpenHelper helper;
    private Context context;
    private SQLiteDatabase sql;
    private ContentValues cv;

    public MyModify(Context c){
        this.context=c;
    }

    public MyModify openCon(){
        helper=new MySQLiteOpenHelper(context);
        sql=helper.getWritableDatabase();
        return this;
    }

    public void closeCon(){
        helper.close();
    }

    public Cursor readDBByWord(String word){
        Cursor cursor=sql.rawQuery("select * from "+MySQLiteOpenHelper.tb
                                    +" where "+MySQLiteOpenHelper.col_word
                                    +" like '%"+word+"%' or "+ MySQLiteOpenHelper.col_definition
                                    +" like '%"+word+"%'",null);
        if(cursor!=null){   cursor.moveToFirst();   }
        return cursor;
    }

    public Cursor readDB(){
        String []allCols=new String[]{
                MySQLiteOpenHelper.col_id,
                MySQLiteOpenHelper.col_word,
                MySQLiteOpenHelper.col_definition
        };

        Cursor cursor=sql.query(MySQLiteOpenHelper.tb,allCols,null,null,null,null,MySQLiteOpenHelper.col_word);
        if(cursor!=null){   cursor.moveToFirst();   }
        return cursor;
    }

public void insertDB(String word,String definition){

        cv=new ContentValues();
        cv.put(MySQLiteOpenHelper.col_word,word);
        cv.put(MySQLiteOpenHelper.col_definition,definition);
        sql.insert(MySQLiteOpenHelper.tb,null,cv);
    }

    public int updateDB(int id,String word,String definition){
        cv=new ContentValues();
        cv.put(MySQLiteOpenHelper.col_word,word);
        cv.put(MySQLiteOpenHelper.col_definition,definition);
        int i=sql.update(MySQLiteOpenHelper.tb,cv,MySQLiteOpenHelper.col_id +"="+id,null);
        return i;
    }

    public void deleteDB(int id){
        sql.delete(MySQLiteOpenHelper.tb, MySQLiteOpenHelper.col_id +"="+id,null);
    }
}