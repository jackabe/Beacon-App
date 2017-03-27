package nsa.com.museum;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Museums> contactList;

    public CustomListAdapter(Context context, ArrayList<Museums> list) {

        this.context = context;
        contactList = list;
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
        TextView museumName = (TextView) convertView.findViewById(R.id.title);
        museumName.setText(contactListItems.getMuseumCity());
        TextView museumOpen = (TextView) convertView.findViewById(R.id.open);
        museumOpen.setText(contactListItems.getMuseumOpen());
        TextView museumClose = (TextView) convertView.findViewById(R.id.close);
        museumClose.setText(contactListItems.getMuseumClose());
        ImageView image = (ImageView) convertView.findViewById(R.id.icon);
        image.setImageResource(R.mipmap.icon);

        return convertView;
    }

}