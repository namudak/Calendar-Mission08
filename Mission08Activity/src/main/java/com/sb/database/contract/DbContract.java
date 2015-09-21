package com.sb.database.contract;

import android.provider.BaseColumns;

/**
 * Created by student on 2015-09-18.
 */

public final class DbContract {
    public DbContract() {}

    public static abstract class UserEntry implements BaseColumns {

        public static final String TABLE_NAME= "Todo";
        public static final String COLUMN_NAME_TIME= "time";
        public static final String COLUMN_NAME_WEATHER= "weather";
        public static final String COLUMN_NAME_HOUR= "hour";
        public static final String COLUMN_NAME_MIN= "min";
        public static final String COLUMN_NAME_TODO= "todo";
    }

}