package de.skubware.opentraining.database;

import android.provider.BaseColumns;

/**
 * @author Igor Bobko <limit-speed@yandex.ru>
 */


public class WeightEntity implements BaseColumns
{
    public static final String TABLE_NAME = "WEIGHT";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    "D INTEGER," +
                    "W REAL" + " );";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
