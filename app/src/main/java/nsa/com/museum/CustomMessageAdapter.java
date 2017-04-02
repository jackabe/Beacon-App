package nsa.com.museum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class CustomMessageAdapter extends BaseAdapter {

    Context context;
    ArrayList<Messages> messageList;
    String messageTitle;
    String messageAnswered;

    // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

    public CustomMessageAdapter(Context context, ArrayList<Messages> list) {

        this.context = context;
        messageList = list;
    }

    @Override
    public int getCount() {

        return messageList.size();
    }

    @Override
    public Object getItem(int position) {

        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        Messages messageListContent = messageList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_row, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(messageListContent.getMessageTitle());


        TextView answered = (TextView) convertView.findViewById(R.id.answered);
        answered.setText("Question answered: " + messageListContent.getmessageAnswered());

        return convertView;
    }

}