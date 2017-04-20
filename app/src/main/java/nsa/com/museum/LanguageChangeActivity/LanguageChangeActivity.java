package nsa.com.museum.LanguageChangeActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import nsa.com.museum.AdminActivity.AdminLogin;
import nsa.com.museum.HelpActivity.HelpActivity;
import nsa.com.museum.HistoryActivity.HistoryActivity;
import nsa.com.museum.MainActivity.MainActivity;
import nsa.com.museum.R;
import nsa.com.museum.SettingsActivity.NewSettingsActivity;

public class LanguageChangeActivity extends AppCompatActivity {

    Button language;
    Button eng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_change);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        language = (Button) findViewById(R.id.lang);
        eng = (Button) findViewById(R.id.eng);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uses code from http://stackoverflow.com/questions/2900023/change-language-programmatically-in-android
                Locale myLocale = new Locale("cy");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(getApplicationContext(), LanguageChangeActivity.class);
                startActivity(refresh);
                finish();
            }
        });

        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uses code from http://stackoverflow.com/questions/2900023/change-language-programmatically-in-android
                Locale myLocale = new Locale("en");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(getApplicationContext(), LanguageChangeActivity.class);
                startActivity(refresh);
                finish();
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
