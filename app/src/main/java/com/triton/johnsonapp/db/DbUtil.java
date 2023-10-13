package com.triton.johnsonapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbUtil {


    static SQLiteDatabase db;
    Context context;
    ContentValues values;
    DbHelper dbHelper;
    private static final int DATABASE_VERSION = 1;
    public DbUtil(Context context) {
        this.context = context;
        values = new ContentValues();
    }

    public SQLiteDatabase open() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        db = dbHelper.getWritableDatabase();
        return db;
    }

    public void close() {
        if (dbHelper != null) {
            db.close();
        }
    }

    public static final String[] DATE_FIELD = {
            DbHelper.ID,
            DbHelper.CURRENT_DATE
    };

    public static final String[] QR_FIELD = {

            DbHelper.JOB_ID,
            DbHelper.QR_CODE,
            DbHelper.QR_COUNT,
            DbHelper.MATLID
    };

    public long addDate(String currentDateandTime) {

        values.clear();
        values.put(DbHelper.CURRENT_DATE,currentDateandTime);
        return db.insert(DbHelper.DATE_TABLE,null,values);
    }

    public Cursor getDate() {
        return db.query(DbHelper.DATE_TABLE,DATE_FIELD,null,null,null,null,null);
    }

    public long addQR(String str_message, String job_id, String str_MaterialID) {

        values.clear();
        values.put(DbHelper.QR_CODE,str_message);
        values.put(DbHelper.JOB_ID,job_id);
        values.put(DbHelper.MATLID,str_MaterialID);
        return db.insert(DbHelper.QR_TABLE,null,values);
    }

    public Cursor getQR(String job_id, String str_MaterialID) {
        return db.query(DbHelper.QR_TABLE,QR_FIELD,DbHelper.JOB_ID + "= '" + job_id + "'"
                + "AND " + DbHelper.MATLID+ "= '" + str_MaterialID + "'",null,null,null,null);
    }

    public Cursor getQRall() {
        return db.query(DbHelper.QR_TABLE,QR_FIELD,null,null,null,null,null);
    }

    public int deleteQR() {
         return db.delete(DbHelper.QR_TABLE,null,null);
    }

    public boolean hasQR(String str_message, String job_id, String str_MaterialID) {
        String abc = DbHelper.QR_CODE + "= '" + str_message + "'" + "AND " + DbHelper.JOB_ID + "= '" + job_id + "'"
                + "AND " + DbHelper.MATLID+ "= '" + str_MaterialID + "'";

        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHelper.QR_TABLE + " WHERE " + abc , null);
        if (cursor.getCount()<= 0 ){
            cursor.close();
            return false;
        }
        cursor.close();
        return  true;
    }
}
