package nsa.com.museum.HelpActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import nsa.com.museum.AdminActivity.AdminLogin;
import nsa.com.museum.HistoryActivity.HistoryActivity;
import nsa.com.museum.MainActivity.MainActivity;
import nsa.com.museum.QuestionActivity.QuestionActivity;
import nsa.com.museum.R;
import nsa.com.museum.SettingsActivity.NewSettingsActivity;

public class HelpActivity extends AppCompatActivity {

    Button moreHelp;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        moreHelp = (Button) findViewById(R.id.stillHelpButton);
        context = getApplicationContext();

        moreHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                startActivity(intent);
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
