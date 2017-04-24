package nsa.com.museum.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import nsa.com.museum.BeaconActivity.BeaconActivity;
import nsa.com.museum.MainActivity.Museums;
import nsa.com.museum.R;

public class CustomListAdapter extends BaseAdapter implements Filterable{

    private Context context;
    private ArrayList<Museums> museumList;
    private int open;
    private int close;
    private Calendar time;
    private int currentHour;
    private String museumCity;

    // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

    public CustomListAdapter(Context context, ArrayList<Museums> list) {

        this.context = context;
        museumList = list;
    }

    @Override
    public int getCount() {

        return museumList.size();
    }

    @Override
    public Object getItem(int position) {

        return museumList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Museums museumListContent = museumList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null);

        }
        final TextView museumName = (TextView) convertView.findViewById(R.id.title);
        museumName.setText(museumListContent.getMuseumCity());
        TextView museumOpen = (TextView) convertView.findViewById(R.id.open);
        TextView museumClose = (TextView) convertView.findViewById(R.id.close);
        museumOpen.setText("Opens at: " + museumListContent.getMuseumOpen());
        museumClose.setText("Closes at: " + museumListContent.getMuseumClose());
        ImageView image = (ImageView) convertView.findViewById(R.id.icon);
        image.setImageResource(R.mipmap.icon);
        TextView state = (TextView) convertView.findViewById(R.id.state);
        open = museumListContent.getMuseumOpen();
        close = museumListContent.getMuseumClose();

        // get the hour of day, 1-24.
        time = Calendar.getInstance();
        currentHour = time.get(Calendar.HOUR_OF_DAY);

        // Check if the user is open open or not by comparing current time with open and close in database.
        if (currentHour >= open && currentHour < close) {
            state.setText("Open");
            state.setTextColor(Color.parseColor("#006600"));
        }

        else if (currentHour < open || currentHour > close) {
            state.setText("Closed");
            state.setTextColor(Color.parseColor("#cc0000"));
        }

        else {
            state.setText("Closed");
            state.setTextColor(Color.RED);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add museum clicked to sp
                museumCity = museumName.getText().toString();
                SharedPreferences museum = context.getSharedPreferences("museum", context.MODE_PRIVATE);
                SharedPreferences.Editor edit = museum.edit();
                edit.putString("museum", museumCity);
                edit.commit();
                Intent beacons = new Intent(context, BeaconActivity.class);
                context.startActivity(beacons);
                Toast.makeText(context, museumCity + " " + context.getString(R.string.museum_loaded), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}