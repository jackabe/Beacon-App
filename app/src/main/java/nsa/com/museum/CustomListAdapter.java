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

public class CustomListAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Museums> contactList;
    ArrayList<Museums> filterList;
    CustomFilter filter;

    public CustomListAdapter(Context context, ArrayList<Museums> list) {

        this.context = context;
        contactList = list;
        this.filterList = contactList;
    }

    @Override
    public int getCount() {

        return contactList.size();
    }

    @Override
    public Object getItem(int position) {

        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Museums contactListItems = contactList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null);

        }
        final TextView museumName = (TextView) convertView.findViewById(R.id.title);
        museumName.setText(contactListItems.getMuseumCity());
        TextView museumOpen = (TextView) convertView.findViewById(R.id.open);
        TextView museumClose = (TextView) convertView.findViewById(R.id.close);
        museumOpen.setText("Opens at: " + contactListItems.getMuseumOpen());
        museumClose.setText("Closes at: " + contactListItems.getMuseumClose());
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

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<Museums> filters = new ArrayList<Museums>();
                for (int i =0; i<filterList.size(); i++) {
                    if(filterList.get(i).getMuseumCity().toUpperCase().contains(constraint)) {
                        Museums museum = contactList.get(i);
                        filters.add(museum);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList = (ArrayList<Museums>) results.values;
            notifyDataSetChanged();

        }
    }



}
