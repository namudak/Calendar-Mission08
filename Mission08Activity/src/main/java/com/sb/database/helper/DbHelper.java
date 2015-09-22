package com.sb.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sb.database.contract.DbContract;

/**
 * Created by student on 2015-09-18.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper sInstance;

    public static final String DB_NAME = "Todo.db";
    public static final int DB_VER = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+ DbContract.DbEntry.TABLE_NAME+ "("+
                    DbContract.DbEntry._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DbContract.DbEntry.COLUMN_NAME_TIME+ " TEXT NOT NULL," +
                    DbContract.DbEntry.COLUMN_NAME_WEATHER+ "," +
                    DbContract.DbEntry.COLUMN_NAME_HOUR+ "," +
                    DbContract.DbEntry.COLUMN_NAME_MIN+ " ," +
                    DbContract.DbEntry.COLUMN_NAME_TODO+ ");";

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DbHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Cursor query(ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();

        // How you want the results sorted in the resulting Cursor
        //String sortOrder =
        //        DbContract.DbEntry.COLUMN_NAME_UPDATED + " DESC";

        Cursor cursor = db.query(
                DbContract.DbEntry.TABLE_NAME,     // The table to query
                DbContract.PROJECTION_ALL,         // The columns to return
                selection,//selection,             // The columns for the WHERE clause
                selectionArgs,//selectionArgs,     // The values for the WHERE clause
                null,                              // group by- don't group the rows
                null,                              // having- don't filter by row groups
                null//sortOrder                    // order by- The sort order
        );

        return cursor;
    }

    public long insert(ContentValues values) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(
                DbContract.DbEntry.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    public int update(ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = getWritableDatabase();

        int count = db.update(
                DbContract.DbEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public int delete(ContentValues values, String selection) {
        SQLiteDatabase db= getWritableDatabase();

        // Issue SQL statement.
        int deleted= db.delete(DbContract.DbEntry.TABLE_NAME, selection, null);

        return deleted;
    }

}
