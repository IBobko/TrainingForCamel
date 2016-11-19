package de.skubware.opentraining.database;

import android.provider.BaseColumns;

/**
 * Created by igor on 17.11.16.
 */

public class OptionEntity implements BaseColumns {
    public static final String TABLE_NAME = "Options";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    "key INTEGER," +
                    "value REAL" + " );";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
