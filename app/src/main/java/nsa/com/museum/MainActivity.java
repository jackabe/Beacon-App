package nsa.com.museum;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> museums = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    EditText postcodeInput;
    ListView museumsList;
    Button searchBtn;
    Button settingsBtn;
    Button historyBtn;
    Button nextBtn;
    Button findBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.icon);

        postcodeInput = (EditText) findViewById(R.id.editSearch);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        museumsList = (ListView) findViewById(R.id.museumsList);
        settingsBtn = (Button) findViewById(R.id.settingsBtn);
        historyBtn = (Button) findViewById(R.id.historyBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        findBtn = (Button) findViewById(R.id.findBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postcode = postcodeInput.getText().toString();
                if (postcode.length() < 6 ) {
                    Toast error = Toast.makeText(getApplicationContext(), "Please enter a 6 character postcode", Toast.LENGTH_SHORT);
                    error.show();
                }
                else {
                    Toast nearYou = Toast.makeText(getApplicationContext(), "Loaded Museums near you", Toast.LENGTH_SHORT);
                    nearYou.show();
                    museums.add("Cardiff");
                    museums.add("Newport");
                    museums.add("Birmingham");
                    museums.add("London");
                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, museums);
                    museumsList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    ((EditText)findViewById(R.id.editSearch)).setText(" ");
                }
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewSettingsActivity.class);
                startActivity(i);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BeaconActivity.class);
                startActivity(i);
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

}

