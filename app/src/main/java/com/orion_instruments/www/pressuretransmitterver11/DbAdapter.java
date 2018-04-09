package com.orion_instruments.www.pressuretransmitterver11;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vineetphatak on 14-02-2018.
 */

class DbAdapter
{
    //define static variable
    public static int dbversion =6;
    public static String dblhs = "LHS";
    public static String dbrhs = "RHS";







    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context) {
            super(context,dblhs,null, dbversion);
    }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS "+dbrhs+" (_id INTEGER PRIMARY KEY autoincrement,MODE,UNIT,R1LS,R1LD,UNIQUE(number))");

        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+dbrhs);
            onCreate(db);
        }
    }

    //establsh connection with SQLiteDataBase
    private final Context c;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqlDb;

    public DbAdapter(Context context) {
        this.c = context;
    }
    public DbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(c);
        sqlDb = dbHelper.getWritableDatabase();
        return this;
    }

    //insert data
    public void insert(String text2,String text3,String text4,String text5) {
        if(!isExist(text3)) {
            sqlDb.execSQL("INSERT INTO contacts (MODE,UNIT,R1LS,R1LD) VALUES('" + text2 + "','" + text3 + "','" + text4 + "','" + text5 + "')");
        }
    }
    //check entry already in database or not
    public boolean isExist(String num){
        String query = "SELECT number FROM contacts WHERE number='"+num+"' LIMIT 1";
        Cursor row = sqlDb.rawQuery(query, null);
        return row.moveToFirst();
    }
    //edit data
    public void update(int id,String text2,String text3,String text4,String text5) {
        sqlDb.execSQL("UPDATE "+dbrhs+" SET MODE='"+text2+"', UNIT='"+text3+"', R1LS='"+text4+"', R1LD='"+text5+"'   WHERE _id=" + id);
    }

    //delete data
    public void delete(int id) {
        sqlDb.execSQL("DELETE FROM "+dbrhs+" WHERE _id="+id);
    }

    //fetch data
    public Cursor fetchAllData() {
        String query = "SELECT * FROM "+dbrhs;
        Cursor row = sqlDb.rawQuery(query, null);
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }

    //fetch data by filter
    public Cursor fetchdatabyfilter(String inputText,String filtercolumn) throws SQLException {
        Cursor row = null;
        String query = "SELECT * FROM "+dbrhs;
        if (inputText == null  ||  inputText.length () == 0)  {
            row = sqlDb.rawQuery(query, null);
        }else {
            query = "SELECT * FROM "+dbrhs+" WHERE "+filtercolumn+" like '%"+inputText+"%'";
            row = sqlDb.rawQuery(query, null);
        }
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }
}
