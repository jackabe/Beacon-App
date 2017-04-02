package nsa.com.museum;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
}
