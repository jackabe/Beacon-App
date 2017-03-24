package nsa.com.museum;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconManagerScanEvents;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconRegion;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManager;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCelliBeacon;

import java.util.ArrayList;
import java.util.List;

public class BeaconActivity extends AppCompatActivity implements GCellBeaconManagerScanEvents {

    GCellBeaconScanManager scanMan;
    ListView lv;
    ArrayAdapter beaconAdap;
    ArrayList<String> beacontest = new ArrayList<>();
    ArrayList<String> historyBeacons = new ArrayList<>();

    int PERM_CODE = 101;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        lv = (ListView) findViewById(R.id.beaconsLv);
        //Handle Permissions
        checkPermissions();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create adapter for beacons
        beaconAdap = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, beacontest);

        lv.setAdapter(beaconAdap);

        // Simulating beacons in the LV for when working outside away from NSA
        beacontest.add("6466346446223");
        beacontest.add("90090990800");
        beacontest.add("473474242446");
        beaconAdap.notifyDataSetChanged();

        //Create a manager with the application context
        scanMan = new GCellBeaconScanManager(this);
        scanMan.enableBlueToothAutoSwitchOn(true);

        //Start a scan
        scanMan.startScanningForBeacons();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), beaconAdap.getItem(i).toString(), Toast.LENGTH_SHORT).show();

                if (beacontest.get(i) == "90090990800") {
                    Uri x = Uri.parse("https://rhp.avoqr.eu/en/musicians");
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, x);
                    startActivity(launchBrowser);
                } else if (beacontest.get(i) == "473474242446") {
                    Uri x = Uri.parse("http://rhp.avoqr.eu/en/majesty");
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, x);
                    startActivity(launchBrowser);

                }

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BeaconActivity.this);
                builder.setTitle("Options");
                final String item = beaconAdap.getItem(i).toString();
                builder.setItems(new String[]{"Add to history", "Cancel"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(getApplicationContext(), item + " Added to history", Toast.LENGTH_SHORT).show();
                                historyBeacons.add(item);
                                Intent intent = new Intent(getBaseContext(), HistoryActivity.class);
                                intent.putStringArrayListExtra("beacons", historyBeacons);
                                beaconAdap.notifyDataSetChanged();
                                startActivity(intent);
                                break;
                            case 1:

                                break;

                        }
                    }
                });
                builder.show();
                return false;
            }
        });
    }


    @Override
    public void onGCellUpdateBeaconList(List<GCelliBeacon> list) {
        for (GCelliBeacon beacon : list) {
            if (beaconAdap.getPosition(beacon.getProxUuid().getStringFormattedUuid()) == -1) {

                beaconAdap.add(beacon.getProxUuid().getStringFormattedUuid());
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

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
