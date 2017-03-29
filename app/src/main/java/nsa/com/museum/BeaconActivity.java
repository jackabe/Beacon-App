package nsa.com.museum;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconManagerScanEvents;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconRegion;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManager;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCelliBeacon;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
public class BeaconActivity extends AppCompatActivity implements GCellBeaconManagerScanEvents {

    GCellBeaconScanManager scanMan;
    ArrayList<String> historyBeacons = new ArrayList<>();
    DBBeacon db;

    CustomBeaconAdapter beaconAdapter;
    ArrayList<Beacons> beaconsArrayList;
    Beacons beaconsListItems;
    ListView lv;
    String aBeacon;
    ArrayList<String> beacons;

    int PERM_CODE = 101;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkPermissions();

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
        String myId = "3FC5BB15-5FAF-4505-BDC8-A49DD6C19A45";

        // lETS CHECK IF IT THE ID IS ALREADY IN THE DATABASE BEFORE WE ADD IT

        String checkIfIn = "SELECT beaconId FROM beaconDetails WHERE beaconId='" + myId + "' ";
        Cursor c2 = db.selectQuery(checkIfIn);

        if (c2.getCount() == 0) {
            Log.i("NotIn", "not in database" + "");
            String query = "INSERT INTO beaconDetails(beaconId, objectName, url) values ('"
                    + "3FC5BB15-5FAF-4505-BDC8-A49DD6C19A45" + "','" + "The Beatles" + "','" + "www.beatles.co.uk" + "')";
            db.executeQuery(query);
            c2.close();
        }

        else {

            String check = "SELECT * FROM beaconDetails WHERE beaconId='" + myId + "' ";
            Cursor c1 = db.selectQuery(check);
            if (c1 != null && c1.getCount() != 0) {
                if (c1.moveToFirst()) {
                    do {

                        beaconsArrayList.clear();
                        beaconsListItems = new Beacons();
                        beaconsListItems.setBeaconId(c1.getString(c1
                                .getColumnIndex("beaconId")));
                        beaconsListItems.setObjectName(c1.getString(c1
                                .getColumnIndex("objectName")));
                        beaconsListItems.setUrl(c1.getString(c1
                                .getColumnIndex("url")));

                        beaconsArrayList.add(beaconsListItems);

                    } while (c1.moveToNext());
                }
                beaconAdapter = new CustomBeaconAdapter(
                        BeaconActivity.this, beaconsArrayList);
                lv.setAdapter(beaconAdapter);
            }
        }
    }

//        if (c2 != null && c2.getCount() != 0) {
//            if (c2.moveToFirst()) {
//                do {
//                    beaconsListItems = new Beacons();
//                    beaconsListItems.setBeaconId(c2.getString(c2
//                            .getColumnIndex("beaconId")));
//                    idinDatabase = beaconsListItems.getBeaconId();
//
//                } while (c2.moveToNext());
//            }
//        }
//
//        if (myId.contains(idinDatabase)) {
//
//        }


//        for (GCelliBeacon beacon : list) {
//
//            String theBeacon = beacon.getProxUuid().getStringFormattedUuid();
//
//            if (!beacons.contains(theBeacon)) {
//                aBeacon = theBeacon;
//                beacons.add(aBeacon);
//                Log.i("BeaconAddedToList", "Beacon added to list" + "");
//            } else if (beacons.contains(theBeacon)) {
//                aBeacon = theBeacon;
//            }
//
//            String check = "SELECT * FROM beaconDetails WHERE beaconId='" + aBeacon + "' ";
//            Log.i("BeaconAddedToList", check + "");
//            Cursor c1 = db.selectQuery(check);
////            Log.i("null", aBeacon + "");
//            if (c1 != null && c1.getCount() != 0) {
//                Log.i("null", "helloooo" + "");
//                if (c1.moveToFirst()) {
//                    do {
//                        beaconsListItems = new Beacons();
//                        beaconsListItems.setBeaconId(c1.getString(c1
//                                .getColumnIndex("beaconsId")));
//                        beaconId = beaconsListItems.getBeaconId();
//                        Log.i("BeaconAddedToList", beaconId + "");
//                     } while (c1.moveToNext());
//
//                        if (aBeacon.contains(beaconId)) {
//                            beaconsArrayList = new ArrayList<>();
//                            beaconsArrayList.clear();
//                            String query = "SELECT * FROM beaconDetails WHERE beaconId='" + aBeacon + "' ";
//                            Cursor c2 = db.selectQuery(query);
//                            if (c2 != null && c2.getCount() != 0) {
//                                if (c2.moveToFirst()) {
//                                    do {
//                                        beaconsListItems = new Beacons();
////                                        Log.i("BeaconAddedToList", "heloooo" + "");
//
//                                        beaconsListItems.setBeaconId(c1.getString(c1
//                                                .getColumnIndex("beaconsId")));
//                                        beaconsListItems.setObjectName(c1.getString(c1
//                                                .getColumnIndex("objectName")));
//                                        beaconsListItems.setUrl(c1.getString(c1
//                                                .getColumnIndex("url")));
//
//                                        beaconsArrayList.add(beaconsListItems);
//
//                                    } while (c1.moveToNext());
//                                }
//                            }
//
//                            c2.close();
//                        }
//                    beaconAdapter = new CustomBeaconAdapter(
//                            BeaconActivity.this, beaconsArrayList);
//                    lv.setAdapter(beaconAdapter);
//                }
//                }
//            }


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
}