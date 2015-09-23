package com.sb.database.contract;

import android.provider.BaseColumns;

/**
 * Created by student on 2015-09-18.
 */

public final class DbContract {

    public static final String[] PROJECTION_ALL = new String[]{
            DbEntry._ID,
            DbEntry.COLUMN_NAME_TIME,
            DbEntry.COLUMN_NAME_TODO,
            DbEntry.COLUMN_NAME_HOUR,
            DbEntry.COLUMN_NAME_MIN,
            DbEntry.COLUMN_NAME_WEATHER
    };

    public DbContract() {}

    public static abstract class DbEntry implements BaseColumns {

        public static final String TABLE_NAME= "Todo";
        public static final String COLUMN_NAME_TIME= "time";
        public static final String COLUMN_NAME_WEATHER= "weather";
        public static final String COLUMN_NAME_HOUR= "hour";
        public static final String COLUMN_NAME_MIN= "min";
        public static final String COLUMN_NAME_TODO= "todo";
    }

}