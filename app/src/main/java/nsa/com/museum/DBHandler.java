package nsa.com.museum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by c1673107 on 17/03/2017.
 */
public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "museum";

    private static final String TABLE_MUSEUM_DETAILS = "museumDetails";

    private static final String MUSEUM_NAME = "museumName";
    private static final int MUSEUM_ID = 1;

    public DBHandler(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_MUSEUM_DETAILS = "CREATE TABLE" + TABLE_MUSEUM_DETAILS + "("
                + MUSEUM_ID + " INTEGER PRIMARY KEY,"
                + MUSEUM_NAME + " TEXT " + ")";
        db.execSQL(CREATE_TABLE_MUSEUM_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSEUM_DETAILS);

        onCreate(db);
    }

}
