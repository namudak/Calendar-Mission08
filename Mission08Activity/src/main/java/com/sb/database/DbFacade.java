package com.sb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.sb.TodoItem;
import com.sb.database.contract.DbContract;
import com.sb.database.helper.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by student on 2015-09-22.
 */
public class DbFacade {
    private DbHelper mHelper;
    private SimpleDateFormat mFormat;

    public DbFacade(Context context) {
        //mHelper = DbHelper.getInstance(context);
        mHelper= DbHelper.getInstance(context);

        mFormat = new SimpleDateFormat("yyyy MM dd");
    }

    /**
     * Add Todo
     *
     * @param calendar
     * @param todo
     * @return 성공, 실패
     */
    public boolean addTodo(Calendar calendar, TodoItem todo) {
        ContentValues values = new ContentValues();

        values.put(DbContract.DbEntry.COLUMN_NAME_TIME,
                mFormat.format(calendar.getTime()));
        values.put(DbContract.DbEntry.COLUMN_NAME_HOUR, todo.getHour());
        values.put(DbContract.DbEntry.COLUMN_NAME_MIN, todo.getMin());
        values.put(DbContract.DbEntry.COLUMN_NAME_TODO, todo.getTodo());
        values.put(DbContract.DbEntry.COLUMN_NAME_WEATHER, todo.getWeather());

        return mHelper.insert(
                values
        ) != -1;
    }

    public int updateTodo(Calendar calendar, TodoItem todo) {
        ContentValues values = new ContentValues();
        values.put(DbContract.DbEntry.COLUMN_NAME_TIME,
                mFormat.format(calendar.getTime()));
        values.put(DbContract.DbEntry.COLUMN_NAME_HOUR, todo.getHour());
        values.put(DbContract.DbEntry.COLUMN_NAME_MIN, todo.getMin());
        values.put(DbContract.DbEntry.COLUMN_NAME_TODO, todo.getTodo());
        values.put(DbContract.DbEntry.COLUMN_NAME_WEATHER, todo.getWeather());

        String selection = DbContract.DbEntry.COLUMN_NAME_TIME + "= ?";
        String[] selectionArgs = {values.getAsString(DbContract.DbEntry.COLUMN_NAME_TIME)};

        return mHelper.update(
                values,
                selection,
                selectionArgs);


    }

    public List<TodoItem> getTodo(Calendar calendar) {
        String calStr = mFormat.format(calendar.getTime());

        Cursor cursor = mHelper.getReadableDatabase().query(
                DbContract.DbEntry.TABLE_NAME,
                DbContract.PROJECTION_ALL,
                DbContract.DbEntry.COLUMN_NAME_TIME + "= ?",
                new String[] {
                        calStr
                },
                null,
                null,
                DbContract.DbEntry.COLUMN_NAME_TIME + " DESC"
        );

        return cursorToList(cursor);
    }

    @NonNull
    private List<TodoItem> cursorToList(Cursor cursor) {
        List<TodoItem> list = new ArrayList<>();

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_TIME));
            String hour = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_HOUR));
            String minute = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_MIN));
            String todo = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_TODO));
            String weather = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_TODO));

            TodoItem todoitem = new TodoItem(time, hour, minute, todo, weather);
            list.add(todoitem);
        }
        cursor.close();
        return list;
    }
}
