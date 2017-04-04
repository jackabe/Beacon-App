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
    public static final String TABLE_HISTORY_DETAILS = "historyDetails";
    public static final String TABLE_MESSAGE_DETAILS = "messageDetails";

    public static final String MESSAGE_ID = "messageID";
    public static final String MESSAGE_TITLE = "messageTitle";
    public static final String MESSAGE_ANSWERED = "messageAnswered";
    public static final String MESSAGE_QUESTION= "messageQuestion";

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

    // FIELDS FOR THE HISTORY TABLE

    public static final String HISTORY_BEACON_ID = "beaconId";


    private static final String CREATE_MUSEUM_DATABASE = "create table "
            + TABLE_MUSEUM_DETAILS + " (" + MUSEUM_ID
            + " integer primary key autoincrement, " + CITY
            + " text not null, " + OPEN_TIME + " integer not null, " + CLOSE_TIME + " integer not null);";

    private static final String CREATE_BEACON_DATABASE = "create table "
            + TABLE_BEACON_DETAILS + " (" + BEACON_ID
            + " text primary key, " + MUSEUM_ID
            + " text not null, " +OBJECT_NAME
            + " text not null, " + WEBSITE_URL + " text not null, " + OBJECT_IMAGE + " blob not null);";

    private static final String CREATE_HISTORY_DATABASE = "create table "
            + TABLE_HISTORY_DETAILS + " (" + HISTORY_BEACON_ID
            + " text primary key, " + OBJECT_NAME
            + " text not null, " + WEBSITE_URL + " text not null);";

    private static final String CREATE_MESSAGE_DETAILS = "create table "
            + TABLE_MESSAGE_DETAILS + " (" + MESSAGE_ID
            + " integer primary key autoincrement, "  + MESSAGE_TITLE
            + " text not null, " + MESSAGE_ANSWERED
            + " text not null, " + MESSAGE_QUESTION + " text not null);";

    // Some Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

    public ItemsDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEUM_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEACON_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE_DETAILS);
        db.execSQL(CREATE_MESSAGE_DETAILS);
        db.execSQL(CREATE_MUSEUM_DATABASE);
        db.execSQL(CREATE_BEACON_DATABASE);
        db.execSQL(CREATE_HISTORY_DATABASE);


        // Default loaded in beacons on apps first startup
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Cardiff" + "','" + 7 + "','" + 1800 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "London" + "','" + 6 + "','" + 1800 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Newport" + "','" + 9 + "','" + 1900 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Manchester" + "','" + 1600 + "','" + 2100 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Evesham" + "','" + 9 + "','" + 1900 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Newcastle" + "','" + 9 + "','" + 1900 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Aston" + "','" + 1700 + "','" + 1900 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Liverpool" + "','" + 7 + "','" + 1700 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Stoke" + "','" + 9 + "','" + 1900 + "')");
        db.execSQL("INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "Kidderminster" + "','" + 7 + "','" + 1500 + "')");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEUM_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEACON_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE_DETAILS);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }





}