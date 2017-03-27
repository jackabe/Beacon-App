package nsa.com.museum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    EditText addCity;
    EditText addOpen;
    EditText addClose;
    Button addMuseum;

    EditText addId;
    EditText addName;
    EditText addLink;
    Button addBeacon;

    String city;
    String open;
    String close;
    String beaconId;
    String name;
    String link;
    DBConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addCity = (EditText) findViewById(R.id.addCity);
        addOpen = (EditText) findViewById(R.id.addOpen);
        addClose = (EditText) findViewById(R.id.addClose);
        addMuseum = (Button) findViewById(R.id.addMuseum);

        addId = (EditText) findViewById(R.id.addId);
        addName = (EditText) findViewById(R.id.addName);
        addLink = (EditText) findViewById(R.id.addLink);
        addBeacon = (Button) findViewById(R.id.addBeacon);

        db = new DBConnector(this);

        addMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = addCity.getText().toString();
                open = addOpen.getText().toString();
                close = addClose.getText().toString();
                String query = "INSERT INTO museumDetails(museumCity, openTime, closeTime) values ('"
                        + city + "','" + open + "','" + close + "')";
                db.executeQuery(query);
                addCity.setText("");
                addOpen.setText("");
                addClose.setText("");
            }
        });

        addBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                beaconId = addId.getText().toString();
//                name = addName.getText().toString();
//                link = addLink.getText().toString();
//                ImageView image = (ImageView) findViewById(R.id.objectIcon);
//
//                image.setDrawingCacheEnabled(true);
//                image.buildDrawingCache();
//                Bitmap bm = image.getDrawingCache();
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//
//                db.addBeacon(beaconId, name, link, byteArray);
//                Snackbar snackbar = Snackbar
//                        .make(v, name + " added to database", Snackbar.LENGTH_LONG);
//                snackbar.show();
//                db.getMuseums("london");
            }
        });
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
