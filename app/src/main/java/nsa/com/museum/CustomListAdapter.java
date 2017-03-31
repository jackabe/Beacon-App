package nsa.com.museum;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Museums> museumList;
    ArrayList<Museums> filterList;

    public CustomListAdapter(Context context, ArrayList<Museums> list) {

        this.context = context;
        museumList = list;
        this.filterList = museumList;
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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent beacons = new Intent(context, BeaconActivity.class);
                context.startActivity(beacons);
                Toast.makeText(context, museumName.getText().toString() + " museum loaded!", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}
