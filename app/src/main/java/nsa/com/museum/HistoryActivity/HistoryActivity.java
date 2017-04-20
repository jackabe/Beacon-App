package nsa.com.museum.HistoryActivity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import nsa.com.museum.AdminActivity.AdminLogin;
import nsa.com.museum.HelpActivity.HelpActivity;
import nsa.com.museum.MainActivity.MainActivity;
import nsa.com.museum.SettingsActivity.NewSettingsActivity;
import nsa.com.museum.R;

public class HistoryActivity extends AppCompatActivity {

    ListView historyLv;
    DBHistory db;
    CustomHistoryAdapter historyAdapter;
    ArrayList<History> historyArrayList;
    History historyListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        historyLv = (ListView) findViewById(R.id.historyView);
        Log.i("check", "out of method" + "");

        db = new DBHistory(this);
        historyArrayList = new ArrayList<>();
        historyArrayList.clear();
        final String query = "SELECT * FROM historyDetails ";
        Cursor c1 = db.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {

                do {
                    historyListItems = new History();
                    historyListItems.setBeaconId(c1.getString(c1
                            .getColumnIndex("beaconId")));
                    historyListItems.setObjectName(c1.getString(c1
                            .getColumnIndex("objectName")));
                    historyListItems.setUrl(c1.getString(c1
                            .getColumnIndex("url")));

                    historyArrayList.add(historyListItems);

                } while (c1.moveToNext());
            }
            historyAdapter = new CustomHistoryAdapter(
                    HistoryActivity.this, historyArrayList);
            historyLv.setAdapter(historyAdapter);

                } while (c1.moveToNext());

        c1.close();
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
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
