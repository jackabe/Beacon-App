package nsa.com.museum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView historyLv;
    ArrayAdapter historyAdap;
    ArrayList<String> historyBeacons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyBeacons = getIntent().getExtras().getStringArrayList("beacons");
        historyAdap = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, historyBeacons);
        historyLv = (ListView) findViewById(R.id.historyView);
        historyLv.setAdapter(historyAdap);
    }
}
