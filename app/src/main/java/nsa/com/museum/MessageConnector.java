package nsa.com.museum;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MessageConnector {

    // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

    public static final String DATABASE_NAME = "MessageDatabase";
    public static final int DATABASE_VERSION = 1;
    SQLiteDatabase sqlDatabase;
    ItemsDBHelper dbHelper;

    public MessageConnector(Context context) {

        dbHelper = new ItemsDBHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        sqlDatabase = dbHelper.getWritableDatabase();
    }

    public void executeQuery(String query) {
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }

            sqlDatabase = dbHelper.getWritableDatabase();
            sqlDatabase.execSQL(query);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }

    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();

            }
            sqlDatabase = dbHelper.getWritableDatabase();
            c1 = sqlDatabase.rawQuery(query, null);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);

        }
        return c1;

    }


}