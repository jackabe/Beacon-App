package nsa.com.museum;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageCenterActivity extends AppCompatActivity {

    ListView messagesLv;
    ArrayList<Messages> messages;
    Messages message;
    String messageTitle;
    String messageAnswered;
    ArrayList messageAdapterList;
    MessageConnector db;
    CustomMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        messagesLv = (ListView) findViewById(R.id.messagesLv);
        db = new MessageConnector(this);

        // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

        // Create new cursor and select all rows from the table
        // Set each value we want from the database show in our list adapter.
        // Add the array list contaning hese values to the custom list adapter.

        messages = new ArrayList<>();
        messages.clear();
        final String query = "SELECT * FROM messageDetails ";
        Cursor c1 = db.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    message = new Messages();

                    message.setMessageTitle(c1.getString(c1
                            .getColumnIndex("messageTitle")));
                    message.setmessageAnswered(c1.getString(c1
                            .getColumnIndex("messageAnswered")));
//                    message.setMessageQuestion(c1.getString(c1
//                            .getColumnIndex("messageQuestion")));

                    messages.add(message);

                } while (c1.moveToNext());
            }
        }

        adapter = new CustomMessageAdapter(
                MessageCenterActivity.this, messages);
        messagesLv.setAdapter(adapter);
        c1.close();

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
