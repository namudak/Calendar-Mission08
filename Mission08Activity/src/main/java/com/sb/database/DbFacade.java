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
import java.util.Date;
import java.util.List;

/**
 * Created by student on 2015-09-22.
 */
public class DbFacade {
    private DbHelper mHelper;
    private SimpleDateFormat mFormat;

    public DbFacade(Context context) {
        //mHelper = DbHelper.getInstance(context);
        mHelper = DbHelper.getInstance(context);

        mFormat = new SimpleDateFormat("yyyy MM dd");
    }

    /**
     * Add Todo
     *
     * @param date
     * @param todo
     * @return boolean for success or not
     */
    public boolean addTodo(Date date, TodoItem todo) {
        ContentValues values = new ContentValues();

        values.put(DbContract.DbEntry.COLUMN_NAME_TIME, mFormat.format(date));
        values.put(DbContract.DbEntry.COLUMN_NAME_HOUR, todo.getHour());
        values.put(DbContract.DbEntry.COLUMN_NAME_MIN, todo.getMin());
        values.put(DbContract.DbEntry.COLUMN_NAME_TODO, todo.getTodo());
        values.put(DbContract.DbEntry.COLUMN_NAME_WEATHER, todo.getWeather());

        return mHelper.insert(
                values
        ) != -1;
    }

    public int updateTodo(Date date, TodoItem todo) {
        ContentValues values = new ContentValues();
        values.put(DbContract.DbEntry.COLUMN_NAME_HOUR, todo.getHour());
        values.put(DbContract.DbEntry.COLUMN_NAME_MIN, todo.getMin());
        values.put(DbContract.DbEntry.COLUMN_NAME_TODO, todo.getTodo());
        values.put(DbContract.DbEntry.COLUMN_NAME_WEATHER, todo.getWeather());

        String selection =
                        DbContract.DbEntry._ID + "= ? ";

        String[] selectionArgs = {
                values.getAsString(DbContract.DbEntry._ID)
        };

        return mHelper.update(
                values,
                selection,
                selectionArgs);

    }

    public List<TodoItem> getTodo(Date date) {
        String calStr = mFormat.format(date);

        Cursor cursor;
        cursor = mHelper.getReadableDatabase().query(
                DbContract.DbEntry.TABLE_NAME,
                DbContract.PROJECTION_ALL,
                DbContract.DbEntry.COLUMN_NAME_TIME + "= ?",
                new String[]{
                        calStr
                },
                null,
                null,
                null
        );

        if (cursor != null) {
            return cursorToList(cursor);
        } else {
            return null;
        }
    }

    @NonNull
    private List<TodoItem> cursorToList(Cursor cursor) {
        List<TodoItem> todolist = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry._ID));
            String time = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_TIME));
            String hour = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_HOUR));
            String minute = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_MIN));
            String todo = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_TODO));
            String weather = cursor.getString(cursor
                    .getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_NAME_WEATHER));

            TodoItem todoitem = new TodoItem(id, time, hour, minute, todo, weather);
            todolist.add(todoitem);
        }
        cursor.close();

        return todolist;
    }

    public int deleteTodo(Date date, TodoItem todo) {
        ContentValues values = new ContentValues();
        values.put(DbContract.DbEntry.COLUMN_NAME_TIME, mFormat.format(date));

        String selection;
        String[] selectionArgs;
        if (todo == null) {
            selection =
                    DbContract.DbEntry.COLUMN_NAME_TIME + "= ? ";

            selectionArgs = new String[]{
                    values.getAsString(DbContract.DbEntry.COLUMN_NAME_TIME)
            };
        } else {
            values.put(DbContract.DbEntry._ID, todo.getId());
            selection =
                    DbContract.DbEntry.COLUMN_NAME_TIME + "= ? and " +
                            DbContract.DbEntry._ID + "= ? ";

            selectionArgs = new String[]{
                    values.getAsString(DbContract.DbEntry.COLUMN_NAME_TIME),
                    values.getAsString(DbContract.DbEntry._ID)
            };

        }

        return mHelper.delete(values, selection, selectionArgs);
    }
}
