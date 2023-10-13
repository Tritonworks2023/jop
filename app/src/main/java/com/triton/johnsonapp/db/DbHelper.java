package com.triton.johnsonapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "johnson.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DATE_QUERY);
        sqLiteDatabase.execSQL(QR_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static  final String DATE_TABLE = "date_table";
    public static  final String ID = "id";
    public static  final String CURRENT_DATE = "currentdate";

    public static final String DATE_QUERY = "CREATE TABLE "
            + DATE_TABLE + " (" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CURRENT_DATE
            + " TEXT );";

    public static final String QR_TABLE = "qr_table";
    public static final String QR_CODE = "qr_code";
    public static final String QR_COUNT = "qr_count";
    public static  final String JOB_ID = "job_id";
    public static final String MATLID = "total_count";

    public static final String QR_QUERY = "CREATE TABLE "
            + QR_TABLE + " (" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QR_CODE
            + " TEXT , " + QR_COUNT
            + " TEXT , " + JOB_ID
            + " TEXT , " + MATLID
            + " TEXT );";
}
