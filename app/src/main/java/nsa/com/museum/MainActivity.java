package nsa.com.museum;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.NotificationCompat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconManagerScanEvents;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconRegion;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManager;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCelliBeacon;

public class MainActivity extends AppCompatActivity implements GCellBeaconManagerScanEvents {

    GCellBeaconScanManager scanMan;
    ArrayAdapter beaconAdap;
    int dID;
    DBConnector db;


    EditText postcodeInput;
    ListView museumsList;
    Button searchBtn;
    Button findBtn;
    CustomListAdapter listAdapter;


    int PERM_CODE = 101;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        beaconAdap = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1);

        db = new DBConnector(this);
        //TODO check location does not exist
        String query = "INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                + "London" + "','" + 9 + "','" + 1800 + "')";
        db.executeQuery(query);


        postcodeInput = (EditText) findViewById(R.id.editSearch);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        museumsList = (ListView) findViewById(R.id.museumsList);
        findBtn = (Button) findViewById(R.id.findBtn);

        dID = 101;
        showList();
        checkPermissions();

        scanMan = new GCellBeaconScanManager(this);
        scanMan.enableBlueToothAutoSwitchOn(true);
        scanMan.startScanningForBeacons();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postcode = postcodeInput.getText().toString();
                if (postcode.length() < 6 ) {
                    Toast error = Toast.makeText(getApplicationContext(), "Please enter a valid postcode", Toast.LENGTH_SHORT);
                    error.show();
                }
                else {
                    Toast nearYou = Toast.makeText(getApplicationContext(), "Loaded Museums near you", Toast.LENGTH_SHORT);
                    nearYou.show();
                    museumsList.setAdapter(listAdapter);
                    ((EditText)findViewById(R.id.editSearch)).setText(" ");
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });


        museumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Loaded: " + listAdapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
                Intent beacons = new Intent(getApplicationContext(), BeaconActivity.class);
                startActivity(beacons);
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });


    }

    private void showList() {

        ArrayList<Museums> museumsArrayList = new ArrayList<>();
        museumsArrayList.clear();
        String query = "SELECT * FROM museumDetails ";
        Cursor c1 = db.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                Log.i("DB", c1.getInt(c1.getColumnIndex("museumOpen")) + "");
                Log.i("DB", c1.getColumnCount() + "");
                do {
                    Museums museumListItems = new Museums();

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

        CustomListAdapter listAdapter = new CustomListAdapter(
                MainActivity.this, museumsArrayList);
        museumsList.setAdapter(listAdapter);

    }

    public static void createNotification(Context ctx, boolean dismiss, int id, String text) {
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(ctx);
        // You can look at other attributes to set but these three MUST be set in order to build
        notifBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle(ctx.getString(R.string.beacons_title)).setContentText(text);
        //We could pass in whether it was actually dismissable and remove the need for an if statement
        notifBuilder.setOngoing(!dismiss);
        //Create an action for when the intent is clicked (just opening this activity)
        Intent resultIntent = new Intent(ctx, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        ctx,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        notifBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, notifBuilder.build());
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onGCellUpdateBeaconList(List<GCelliBeacon> list) {
        for (GCelliBeacon beacon : list) {
            if(beaconAdap.getPosition(beacon.getProxUuid().getStringFormattedUuid()) == -1) {

                beaconAdap.add(beacon.getProxUuid().getStringFormattedUuid());
                createNotification(getApplicationContext(), true, dID, getString(R.string.beacons_text) + "" + list.size() + "" + (R.string.beacons_text_2));

            }
        }

    }

    /**
     * Ignore ALL of the methods below
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
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermissions(getApplicationContext(), permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERM_CODE);
        }
    }

    /**
     * Iterate through all permissions provided and ensure all have been approved
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






