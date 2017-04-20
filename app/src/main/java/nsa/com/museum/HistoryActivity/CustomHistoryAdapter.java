package nsa.com.museum.HistoryActivity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import nsa.com.museum.R;

public class CustomHistoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<History> historyList;
    String beacon;
    History historyListItems;
    String beaconId;
    String beaconName;
    String url;
    DBHistory db;

    // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.


    public CustomHistoryAdapter(Context context, ArrayList<History> list) {

        this.context = context;
        historyList = list;
        db = new DBHistory(context);
    }

    @Override
    public int getCount() {

        return historyList.size();
    }

    @Override
    public Object getItem(int position) {

        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        historyListItems = historyList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.history_row, null);

        }
        final TextView objectName = (TextView) convertView.findViewById(R.id.name);
        objectName.setText(historyListItems.getObjectName());
        Log.i("check", objectName.getText().toString());

//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 100, false));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, objectName.getText().toString() + context.getString(R.string.loaded), Toast.LENGTH_SHORT).show();
                Uri url = Uri.parse("https://" + historyListItems.getUrl().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                context.startActivity(intent);
            }
        });


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                beacon = objectName.getText().toString();
//                beaconId = historyListItems.getBeaconId();
//                beaconName = historyListItems.getObjectName();
//                url = historyListItems.getUrl();

                AlertDialog.Builder options = new AlertDialog.Builder(context);
                options.setTitle(context.getString(R.string.options));
                options.setItems(new String[]{context.getString(R.string.open), context.getString(R.string.delete), context.getString(R.string.cancel)}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                Toast.makeText(context, objectName.getText().toString() + " " + context.getString(R.string.loaded), Toast.LENGTH_SHORT).show();
                                Uri url = Uri.parse("https://" + historyListItems.getUrl().toString());
                                Intent intentUrl = new Intent(Intent.ACTION_VIEW, url);
                                context.startActivity(intentUrl);
                                break;
                            case 1:
                                String delQuery = "DELETE FROM historyDetails WHERE beaconId='"+beacon+"' ";
                                db.executeQuery(delQuery);
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                options.show();
                return false;
            }
        });

        return convertView;
    }
}