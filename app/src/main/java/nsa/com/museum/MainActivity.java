package nsa.com.museum;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.NotificationCompat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconManagerScanEvents;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconRegion;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManager;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCelliBeacon;

public class MainActivity extends AppCompatActivity implements GCellBeaconManagerScanEvents {

    GCellBeaconScanManager scanMan;
    int dID;
    DBConnector db;
    DBBeacon dbBeacon;
    EditText cityInput;
    ListView museumsList;
    Button searchBtn;
    Button findBtn;
    CustomListAdapter listAdapter;
    ArrayList<Museums> museumsArrayList;
    Museums museumListItems;
    ArrayList<Beacons> beaconsArrayList;
    String aBeacon;
    ArrayList<String> beacons;
    TextView connection;
    int counter;
    InternetConnection internetConnection;

    int PERM_CODE = 101;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load in our toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkPermissions();

        // initialise all our fields
        cityInput = (EditText) findViewById(R.id.editSearch);
        dbBeacon = new DBBeacon(this);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        museumsList = (ListView) findViewById(R.id.museumsList);
        findBtn = (Button) findViewById(R.id.findBtn);
        db = new DBConnector(this);
        scanMan = new GCellBeaconScanManager(this);
        scanMan.enableBlueToothAutoSwitchOn(true);
        scanMan.startScanningForBeacons();
        dID = 101;
        beaconsArrayList = new ArrayList<>();
        connection = (TextView) findViewById(R.id.connection);
        beacons = new ArrayList<>();

        // Check if the user has already seen the no connection page
        SharedPreferences connectionPref = getSharedPreferences("connection", 0);
        counter = connectionPref.getInt("connected", 0);

        // Check if user has internet and set the text to show if they are
        internetConnection = new InternetConnection();
        if (internetConnection.isConnected()) {
            connection.setText("Internet Connected");
        } else {
            connection.setText("No Internet");

            if (counter < 1) {
                // Counter is how many times they have seen it
                Intent noConnection = new Intent(getApplicationContext(), Connection.class);
                startActivity(noConnection);
            }
        }

        // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

        // Create new cursor and select all rows from the table
        // Set each value we want from the database show in our list adapter.
        // Add the array list contaning these values to the custom list adapter.
        museumsArrayList = new ArrayList<>();
        museumsArrayList.clear();
        final String query = "SELECT * FROM museumDetails ";
        Cursor c1 = db.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                Log.i("DB", c1.getInt(c1.getColumnIndex("museumOpen")) + "");

                Log.i("DB", c1.getColumnCount() + "");

                do {
                    museumListItems = new Museums();

                    museumListItems.setMuseumCity(c1.getString(c1
                            .getColumnIndex("museumCity")));
                    museumListItems.setMuseumOpen(c1.getInt(c1
                            .getColumnIndex("museumOpen")));
                    museumListItems.setMuseumClose(c1.getInt(c1
                            .getColumnIndex("museumClose")));

                    museumsArrayList.add(museumListItems);

                } while (c1.moveToNext());
            }
        }
        c1.close();

        listAdapter = new CustomListAdapter(
                MainActivity.this, museumsArrayList);
        museumsList.setAdapter(listAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get edittext to string
                String city = cityInput.getText().toString();
                // Check if input is empty and dosn't contain a letter.
                if (city.contains("") & !city.matches("[a-zA-Z]+")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.empty), Toast.LENGTH_SHORT).show();
                } else {
                    // Capital letter code referenced from http://www.java2s.com/Tutorial/Java/0040__Data-Type/Makesthefirstlettercapsandtherestlowercase.htm.
                    String letter1 = city.substring(0, 1).toUpperCase();
                    String letter2 = city.substring(1).toLowerCase();
                    String capitalisedCity = letter1 + letter2;
                    museumsArrayList.clear();
                    museumsArrayList = new ArrayList<>();
                    final String query = "SELECT * FROM museumDetails WHERE museumCity='" + capitalisedCity + "' ";
                    Cursor c1 = db.selectQuery(query);
                    if (c1 != null && c1.getCount() != 0) {
                        if (c1.moveToFirst()) {

                            do {
                                museumListItems = new Museums();

                                museumListItems.setMuseumCity(c1.getString(c1
                                        .getColumnIndex("museumCity")));
                                museumListItems.setMuseumOpen(c1.getInt(c1
                                        .getColumnIndex("museumOpen")));
                                museumListItems.setMuseumClose(c1.getInt(c1
                                        .getColumnIndex("museumClose")));

                                museumsArrayList.add(museumListItems);

                            } while (c1.moveToNext());
                        }
                    }
                    c1.close();

                    listAdapter = new CustomListAdapter(
                            MainActivity.this, museumsArrayList);
                    museumsList.setAdapter(listAdapter);

                    // Set city input clear and hide keyboard.
                    ((EditText) findViewById(R.id.editSearch)).setText(" ");
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    // Hide keybaord found on http://www.java2s.com/Tutorial/Java/0040__Data-Type/Makesthefirstlettercapsandtherestlowercase.htm
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });


        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });


        // Inserting some default beacons and images for testing
        Bitmap majesty = BitmapFactory.decodeResource(getResources(), R.drawable.abby);
        Bitmap mad = BitmapFactory.decodeResource(getResources(), R.drawable.mad);
        Bitmap beatles = BitmapFactory.decodeResource(getResources(), R.drawable.beatles);
        byte[] beatlesByte = SetImage.getBytes(beatles);
        byte[] madByte = SetImage.getBytes(mad);
        byte[] majestysByte = SetImage.getBytes(majesty);
        String delQuery = "DELETE FROM beaconDetails WHERE beaconId='"+"3FC5BB15-5FAF-4505-BDC8-A49DD6C19A45"+"' ";
        dbBeacon.executeQuery(delQuery);
        String del2 = "DELETE FROM beaconDetails WHERE beaconId='"+"96530D4D-09AF-4159-B99E-951A5E826584"+"' ";
        dbBeacon.executeQuery(del2);
        String del3 = "DELETE FROM beaconDetails WHERE beaconId='"+"01E82601-8329-4BD6-A126-8A17B03D55EC"+"' ";
        dbBeacon.executeQuery(del3);

        dbBeacon.insert("3FC5BB15-5FAF-4505-BDC8-A49DD6C1", "Cardiff", "Majesty", "rhp.avoqr.eu/en/majesty", majestysByte);
        dbBeacon.insert("96530D4D-09AF-4159-B99E-951A5E826584", "Cardiff", "Madeleine Peyroux", "rhp.avoqr.eu/en/musicians", madByte);
        dbBeacon.insert("01E82601-8329-4BD6-A126-8A17B03D55EC", "Cardiff", "The Beatles", "www.thebeatles.com", beatlesByte);
    }

    public static void createNotification(Context context, boolean dismiss, int id) {
        // Code referenced from https://developer.android.com/guide/topics/ui/notifiers/notifications.html

        NotificationCompat.Builder notif = new NotificationCompat.Builder(context);
        // You can look at other attributes to set but these three MUST be set in order to build
        notif.setSmallIcon(R.mipmap.icon_inverted).setContentTitle(context.getString(R.string.beacons_title)).setContentText(context.getString(R.string.click_me));
        //We could pass in whether it was actually dismissable and remove the need for an if
        notif.setOngoing(!dismiss);
        //Create an action for when the intent is clicked (just opening this activity)
        Intent intent = new Intent(context, BeaconActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        notif.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, notif.build());
    }

    //TODO 3 add a click listener to the dismiss button to hide any notification that is showing
    public void dismissNotification(int id) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handles the users choice
        // Each chase is a new intent
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(getApplicationContext(), NewSettingsActivity.class);
                startActivity(settings);
                return true;

            case R.id.action_help:
                Intent help = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(help);
                return true;

            case R.id.action_login:
                Intent login = new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(login);
                return true;

            case R.id.action_home:
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                return true;

            case R.id.action_history:
                Intent history = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(history);
                return true;

            default:
                // If action not recognised.
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onGCellUpdateBeaconList(List<GCelliBeacon> list) {
        Log.i("BEACONS", list.size() + "");
        for (GCelliBeacon beacon : list) {

            String theBeacon = beacon.getProxUuid().getStringFormattedUuid();

            if (!beacons.contains(theBeacon)) {
                aBeacon = theBeacon;
                beacons.add(aBeacon);
                Log.i("BeaconAddedToList", "Beacon added to list" + "");
                String checkIfIn = "SELECT beaconId FROM beaconDetails WHERE beaconId='" + aBeacon + "' ";
                Cursor c2 = dbBeacon.selectQuery(checkIfIn);

                if (c2.getCount() == 0) {
                    Log.i("NotIn", "not in database" + "");
//                beaconsArrayList.clear();
                    c2.close();
                } else {
                    Log.i("BEACONS", "HEllo" + "");
                    createNotification(getApplicationContext(), true, dID);
                }
            }
        }
    }

    /**
     * Ignore ALL of the methods below
     *
     * @param gCellBeaconRegion
     */

    @Override
    public void didEnterBeaconRegion(GCellBeaconRegion gCellBeaconRegion) {

    }

    @Override
    public void didExitBeaconRegion(GCellBeaconRegion gCellBeaconRegion) {

    }

    @Override
    public void didRangeBeaconsinRegion(GCellBeaconRegion gCellBeaconRegion, List<GCelliBeacon> list) {

    }

    @Override
    public void bleNotSupported() {

    }

    @Override
    public void bleNotEnabled() {

    }

    @Override
    public void locationPermissionsDenied() {

    }

    public void checkPermissions() {
        //Request permission if ANY permissions have been denied
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermissions(getApplicationContext(), permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERM_CODE);
        }
    }

    /**
     * Iterate through all permissions provided and ensure all have been approved
     *
     * @param context
     * @param permissions
     * @return
     */
    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Called when the user has dealt with the permissions box and we are told if they granted or denied access
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001: {
                /**
                 * In order to not bother users, we should only be asking for permissions when they perform actions that require it.
                 * So, asking for 10 permissions each time an app starts is bad practice.
                 * We should ask for each when they do something that requires (such as disabling wifi or taking a picture)
                 */
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
                return;
            }
        }
    }

}



