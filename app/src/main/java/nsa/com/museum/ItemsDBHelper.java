package nsa.com.museum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.MessageFormat;

import java.net.URL;

/**
 * Created by c1673107 on 17/03/2017.
 */
public class ItemsDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "museum.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_MUSEUM_DETAILS = "museumDetails";
    public static final String TABLE_BEACON_DETAILS = "beaconDetails";

    // FIELDS FOR THE MUSEUMS TABLE

    public static final String MUSEUM_ID = "museumId";
    public static final String CITY = "museumCity";
    public static final String OPEN_TIME = "museumOpen";
    public static final String CLOSE_TIME = "museumClose";

    // FIELDS FOR THE BEACON TABLE

    public static final String BEACON_ID = "beaconId";
    public static final String OBJECT_NAME = "objectName";
    public static final String WEBSITE_URL = "url";
    public static final String OBJECT_IMAGE = "objectImage";

    private static final String CREATE_MUSEUM_DATABASE = "create table "
            + TABLE_MUSEUM_DETAILS + " (" + MUSEUM_ID
            + " integer primary key autoincrement, " + CITY
            + " text not null, " + OPEN_TIME + " integer not null, " + CLOSE_TIME + " integer not null);";

    private static final String CREATE_BEACON_DATABASE = "create table "
            + TABLE_BEACON_DETAILS + " (" + BEACON_ID
            + " integer primary key, " + OBJECT_NAME
            + " text not null, " + WEBSITE_URL + " text not null);";

    public ItemsDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEUM_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEACON_DETAILS);
        db.execSQL(CREATE_MUSEUM_DATABASE);
        db.execSQL(CREATE_BEACON_DATABASE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEUM_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEACON_DETAILS);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }





}
