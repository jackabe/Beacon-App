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
    Button deleteMuseum;

    EditText addId;
    EditText addName;
    EditText addLink;
    Button addBeacon;
    Button deleteBeacon;

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
        deleteMuseum = (Button) findViewById(R.id.deleteMuseum);

        addId = (EditText) findViewById(R.id.addId);
        addName = (EditText) findViewById(R.id.addName);
        addLink = (EditText) findViewById(R.id.addLink);
        addBeacon = (Button) findViewById(R.id.addBeacon);
        deleteBeacon = (Button) findViewById(R.id.deleteBeacon);

        db = new DBConnector(this);

        addMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = addCity.getText().toString();
                open = addOpen.getText().toString();
                close = addClose.getText().toString();
                String query = "INSERT INTO museumDetails(museumCity, museumOpen, museumClose) values ('"
                        + city + "','" + open + "','" + close + "')";
                db.executeQuery(query);
                addCity.setText("");
                addOpen.setText("");
                addClose.setText("");
                Toast.makeText(getApplicationContext(), city + " added to database", Toast.LENGTH_SHORT).show();
            }
        });

        deleteMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = addCity.getText().toString();
                String delQuery = "DELETE FROM museumDetails WHERE museumCity='"+city+"' ";
                db.executeQuery(delQuery);
                addCity.setText("");
                Toast.makeText(getApplicationContext(), city + " deleted from database", Toast.LENGTH_SHORT).show();
            }
        });

        addBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = addId.getText().toString();
                beaconId = addName.getText().toString();
                link = addLink.getText().toString();
                String query = "INSERT INTO beaconDetails(beaconId, objectName, url) values ('"
                        + beaconId + "','" + name + "','" + link + "')";
                db.executeQuery(query);
                addId.setText("");
                addName.setText("");
                addLink.setText("");
                Toast.makeText(getApplicationContext(), name + " added to database", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = addName.getText().toString();
                String delQuery = "DELETE FROM beaconDetails WHERE objectName='"+name+"' ";
                db.executeQuery(delQuery);
                addName.setText("");
                Toast.makeText(getApplicationContext(), name + " deleted from database", Toast.LENGTH_SHORT).show();
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
