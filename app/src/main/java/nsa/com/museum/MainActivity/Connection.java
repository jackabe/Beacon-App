package nsa.com.museum.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import nsa.com.museum.MainActivity.MainActivity;
import nsa.com.museum.R;

public class Connection extends AppCompatActivity {

    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        continueButton = (Button) findViewById(R.id.continueButton);

        // Add to shared preferences that they have seen this page.
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                SharedPreferences connection = getSharedPreferences("connection", 0);
                SharedPreferences.Editor edit = connection.edit();
                edit.putInt("connected",1);
                edit.commit();
            }
        });
    }
}
