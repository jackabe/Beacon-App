package nsa.com.museum.QuestionActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nsa.com.museum.AdminActivity.AdminLogin;
import nsa.com.museum.HelpActivity.HelpActivity;
import nsa.com.museum.MainActivity.MainActivity;
import nsa.com.museum.MessageActivity.MessageConnector;
import nsa.com.museum.R;
import nsa.com.museum.SettingsActivity.NewSettingsActivity;

public class QuestionActivity extends AppCompatActivity {

    private EditText questionTitle;
    private EditText questionText;
    private Button submit;
    private MessageConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionText = (EditText) findViewById(R.id.title);
        questionTitle = (EditText) findViewById(R.id.question);
        submit = (Button) findViewById(R.id.submitQuestion);
        db = new MessageConnector(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionTextString = questionText.getText().toString();
                String questionTitleString = questionTitle.getText().toString();
                questionText.setText("");
                questionTitle.setText("");

                String query = "INSERT INTO messageDetails(messageTitle, messageAnswered, messageQuestion) values ('"
                        + questionTextString + "','" + "No" + "','" + questionTitleString + "')";
                db.executeQuery(query);
                Toast.makeText(getApplicationContext(), getString(R.string.flagged), Toast.LENGTH_LONG).show();
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

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
