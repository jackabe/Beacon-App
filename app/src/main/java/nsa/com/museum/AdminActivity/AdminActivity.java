package nsa.com.museum.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nsa.com.museum.DBConnector;
import nsa.com.museum.HelpActivity.HelpActivity;
import nsa.com.museum.HistoryActivity.HistoryActivity;
import nsa.com.museum.MessageActivity.MessageCenterActivity;
import nsa.com.museum.SettingsActivity.NewSettingsActivity;
import nsa.com.museum.R;

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

    Button messages;

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
        messages = (Button) findViewById(R.id.messages);

        db = new DBConnector(this);

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageCenterActivity.class);
                startActivity(intent);
            }
        });

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

            case R.id.action_history:
                Intent history = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(history);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}