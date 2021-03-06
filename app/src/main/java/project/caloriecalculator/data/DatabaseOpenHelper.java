package project.caloriecalculator.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * A class that manages database from assets folder.
 */
public final class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
