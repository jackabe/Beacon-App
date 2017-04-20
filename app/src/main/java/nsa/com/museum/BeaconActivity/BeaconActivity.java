package nsa.com.museum.BeaconActivity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconManagerScanEvents;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconRegion;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManager;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCelliBeacon;

import java.util.ArrayList;
import java.util.List;

import nsa.com.museum.AdminActivity.AdminLogin;
import nsa.com.museum.HelpActivity.HelpActivity;
import nsa.com.museum.HistoryActivity.HistoryActivity;
import nsa.com.museum.MainActivity.MainActivity;
import nsa.com.museum.SettingsActivity.NewSettingsActivity;
import nsa.com.museum.R;

public class BeaconActivity extends AppCompatActivity implements GCellBeaconManagerScanEvents {
    GCellBeaconScanManager scanMan;
    DBBeacon db;

    CustomBeaconAdapter beaconAdapter;
    ArrayList<Beacons> beaconsArrayList;
    Beacons beaconsListItems;
    ListView lv;
    String aBeacon;
    ArrayList<String> beacons;

    int PERM_CODE = 101;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERM_CODE);

        lv = (ListView) findViewById(R.id.beaconsLv);
        beaconsArrayList = new ArrayList<>();
        db = new DBBeacon(this);
        beacons = new ArrayList<>();

        scanMan = new GCellBeaconScanManager(this);
        scanMan.enableBlueToothAutoSwitchOn(true);
        scanMan.startScanningForBeacons();

    }

    @Override
    public void onGCellUpdateBeaconList(List<GCelliBeacon> list) {

        // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

        Log.i("BEACONS", list.size() + "");
        for (GCelliBeacon beacon : list) {

            String theBeacon = beacon.getProxUuid().getStringFormattedUuid();

            if (!beacons.contains(theBeacon)) {
                aBeacon = theBeacon;
                beacons.add(aBeacon);
                Log.i("BeaconAddedToList", "Beacon added to list" + "");
                String checkIfIn = "SELECT beaconId FROM beaconDetails WHERE beaconId='" + aBeacon + "' ";
                Cursor c2 = db.selectQuery(checkIfIn);

                if (c2.getCount() == 0) {
                    Log.i("NotIn", "not in database" + "");
//                beaconsArrayList.clear();
                    c2.close();
                }

                else {
                    String check = "SELECT * FROM beaconDetails WHERE beaconId='" + aBeacon + "' ";
                    Cursor c1 = db.selectQuery(check);
                    if (c1 != null && c1.getCount() != 0) {
                        if (c1.moveToFirst()) {
                            do {

                                beaconsListItems = new Beacons();
                                beaconsListItems.setBeaconId(c1.getString(c1
                                        .getColumnIndex("beaconId")));
                                beaconsListItems.setObjectName(c1.getString(c1
                                        .getColumnIndex("objectName")));
                                beaconsListItems.setUrl(c1.getString(c1
                                        .getColumnIndex("url")));
                                beaconsListItems.setImage(c1.getBlob(c1
                                        .getColumnIndex("objectImage")));

                                beaconsArrayList.add(beaconsListItems);

                            } while (c1.moveToNext());
                        }
                        beaconAdapter = new CustomBeaconAdapter(
                                BeaconActivity.this, beaconsArrayList);
                        lv.setAdapter(beaconAdapter);

                        c1.close();
                    }
                }
            } else if (beacons.contains(theBeacon)) {
                aBeacon = theBeacon;
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
//        Request permission if ANY permissions have been denied
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
                Log.i("PERM", permission);
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
            case R.id.action_home:
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                return true;
            case R.id.action_history:
                Intent history = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(history);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}