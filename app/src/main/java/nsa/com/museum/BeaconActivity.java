package nsa.com.museum;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import java.lang.Object;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.sql.Blob;
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
    String museumCity;

    int PERM_CODE = 101;
//    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check which museum they click on, it is sotred in the sp.
        SharedPreferences museum = getSharedPreferences("museum", 0);
        museumCity = museum.getString("museum", "museum");

        lv = (ListView) findViewById(R.id.beaconsLv);
        beaconsArrayList = new ArrayList<>();
        db = new DBBeacon(this);
        beacons = new ArrayList<>();

        scanMan = new GCellBeaconScanManager(this);
        scanMan.enableBlueToothAutoSwitchOn(true);
        scanMan.startScanningForBeacons();

        // Coverting all the images to insert into the database from png's to bitmaps to bytes.
        // Code here referenced from http://stackoverflow.com/questions/20700181/convert-imageview-in-bytes-android.
        Bitmap majesty = BitmapFactory.decodeResource(getResources(), R.drawable.abby);
        Bitmap mad = BitmapFactory.decodeResource(getResources(), R.drawable.mad);
        Bitmap beatles = BitmapFactory.decodeResource(getResources(), R.drawable.beatles);
        byte[] beatlesByte = SetImage.getBytes(beatles);
        byte[] madByte = SetImage.getBytes(mad);
        byte[] majestysByte = SetImage.getBytes(majesty);

//        String delQuery = "DELETE FROM beaconDetails WHERE beaconId='"+"3FC5BB15-5FAF-4505-BDC8-A49DD6C19A45"+"' ";
//        db.executeQuery(delQuery);
//        String del2 = "DELETE FROM beaconDetails WHERE beaconId='"+"96530D4D-09AF-4159-B99E-951A5E826584"+"' ";
//        db.executeQuery(del2);
//        String del3 = "DELETE FROM beaconDetails WHERE beaconId='"+"01E82601-8329-4BD6-A126-8A17B03D55EC"+"' ";
//        db.executeQuery(del3);

        db.insert("3FC5BB15-5FAF-4505-BDC8-A49DD6C1", "Cardiff", "Majesty", "http://rhp.avoqr.eu/en/majesty", majestysByte);
        db.insert("96530D4D-09AF-4159-B99E-951A5E826584", "Cardiff", "Madeleine Peyroux", "www.thebeatles.com", madByte);
        db.insert("01E82601-8329-4BD6-A126-8A17B03D55EC", "Cardiff", "The Beatles", "www.thebeatles.com", beatlesByte);
    }

    @Override
    public void onGCellUpdateBeaconList(List<GCelliBeacon> list) {
        for (GCelliBeacon beacon : list) {

            String theBeacon = beacon.getProxUuid().getStringFormattedUuid();

            // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

            // Check if the beacon found has already been seen and is a valid one from the database.
            // Get the beacon id and compared it with all in database.
            // Create new cursor and select all rows from the table
            // Set each value we want from the database show in our list adapter.
            // Add the array list contaning these values to the custom list adapter.

            if (!beacons.contains(theBeacon)) {
                aBeacon = theBeacon;
                beacons.add(aBeacon);
                Log.i("BeaconAddedToList", "Beacon added to list" + "");
                String checkIfIn = "SELECT beaconId FROM beaconDetails WHERE beaconId='" + aBeacon + "' ";
                Cursor c2 = db.selectQuery(checkIfIn);

                if (c2.getCount() == 0) {
                    Log.i("NotIn", "not in database" + "");
                    beaconsArrayList.clear();
                    c2.close();
                }

                else {
                    String check = "SELECT * FROM beaconDetails WHERE beaconId='" + aBeacon + "WHERE museumId='" + museumCity + "' ";
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
                                byte[] b = c1.getBlob(c1.getColumnIndex("objectImage"));
                                beaconsListItems.setImage(c1.getBlob(c1
                                        .getColumnIndex("objectImage")));

                                beaconsArrayList.add(beaconsListItems);

                            } while (c1.moveToNext());
                        }
                        beaconAdapter = new CustomBeaconAdapter(
                                BeaconActivity.this, beaconsArrayList);
                        lv.setAdapter(beaconAdapter);
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
        //Request permission if ANY permissions have been denied
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasPermissions(getApplicationContext(), permissions)) {
//            ActivityCompat.requestPermissions(this, permissions, PERM_CODE);
//        }
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
            case R.id.action_home:
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}