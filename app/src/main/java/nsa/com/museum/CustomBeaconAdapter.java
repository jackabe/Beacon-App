package nsa.com.museum;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.SyncStateContract;
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

public class CustomBeaconAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Beacons> beaconList;
    ArrayList<Beacons> filterList;
    CustomFilter filter;
    String beacon;
    Beacons beaconListItems;

    public CustomBeaconAdapter(Context context, ArrayList<Beacons> list) {

        this.context = context;
        beaconList = list;
        this.filterList = beaconList;
    }

    @Override
    public int getCount() {

        return beaconList.size();
    }

    @Override
    public Object getItem(int position) {

        return beaconList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        beaconListItems = beaconList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.beacon_row, null);

        }
        final TextView objectName = (TextView) convertView.findViewById(R.id.name);
        objectName.setText(beaconListItems.getObjectName());


        Bitmap bmp = BitmapFactory.decodeByteArray(beaconListItems.getImage(), 0, beaconListItems.getImage().length);
        ImageView image = (ImageView) convertView.findViewById(R.id.objectIcon);
        image.setImageBitmap(bmp);
        Log.i("ImageInDatabase", image + "");


//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200, 100, false));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, objectName.getText().toString() + " loaded!", Toast.LENGTH_SHORT).show();
                Uri url = Uri.parse("https://" + beaconListItems.getUrl().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                context.startActivity(intent);
            }
        });


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                beacon = objectName.getText().toString();
                AlertDialog.Builder options = new AlertDialog.Builder(context);
                options.setTitle("Choices");
                options.setItems(new String[]{"Add to history", "Open", "Cancel"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                Toast.makeText(context, beacon + " added to history", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, HistoryActivity.class);
                                context.startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(context, objectName.getText().toString() + " loaded!", Toast.LENGTH_SHORT).show();
                                Uri url = Uri.parse("https://" + beaconListItems.getUrl().toString());
                                Intent intentUrl = new Intent(Intent.ACTION_VIEW, url);
                                context.startActivity(intentUrl);
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

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
//
//            if (constraint != null && constraint.length() > 0) {
//                constraint = constraint.toString().toUpperCase();
//                ArrayList<Beacons> filters = new ArrayList<>();
//                for (int i =0; i<filterList.size(); i++) {
//                    if(filterList.get(i).getObjectName().toUpperCase().contains(constraint)) {
//                        Beacons beacon = beaconList.get(i);
//                        filters.add(beacon);
//                    }
//                }
//                results.count = filterList.size();
//                results.values = filterList;
//            }
            return results;
//        }
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            beaconList = (ArrayList<Beacons>) results.values;
            notifyDataSetChanged();

        }
    }



}