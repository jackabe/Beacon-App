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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.filter;

public class CustomBeaconAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Beacons> beaconList;
    String beacon;
    Beacons beaconListItems;
    InternetConnection internetConnection;

    // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

    public CustomBeaconAdapter(Context context, ArrayList<Beacons> list) {

        this.context = context;
        beaconList = list;
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

        // Get our custom list row
        beaconListItems = beaconList.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.beacon_row, null);

        }

        // Set our custom list row fields with the values in the database.
        final TextView objectName = (TextView) convertView.findViewById(R.id.name);
        objectName.setText(beaconListItems.getObjectName());


        Bitmap bmp = BitmapFactory.decodeByteArray(beaconListItems.getImage(), 0, beaconListItems.getImage().length);
        ImageView image = (ImageView) convertView.findViewById(R.id.objectIcon);
        image.setImageBitmap(bmp);
        Log.i("ImageInDatabase", image + "");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Loads url from database when clicked.
                // We pass the string url into the method
                Toast.makeText(context, objectName.getText().toString() + context.getString(R.string.loaded), Toast.LENGTH_SHORT).show();
                Uri url = Uri.parse("https://" + beaconListItems.getUrl().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                context.startActivity(intent);
            }
        });


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Check if the user has internet connection.
                // Give user options when they hold click.
                internetConnection = new InternetConnection();
                beacon = objectName.getText().toString();
                AlertDialog.Builder options = new AlertDialog.Builder(context);
                options.setTitle(context.getString(R.string.options));
                options.setItems(new String[]{context.getString(R.string.add), context.getString(R.string.open), context.getString(R.string.cancel)}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                Toast.makeText(context, beacon + " " + context.getString(R.string.added_history), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, HistoryActivity.class);
                                context.startActivity(intent);
                                break;
                            case 1:
                                // This code referenced from here http://stackoverflow.com/questions/14670638/webview-load-website-when-online-load-local-file-when-offline
                                // If connection load up and add to cache so it can be loaded again without internet.
                                WebView webView = new WebView( context );
                                webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
                                webView.getSettings().setAppCachePath( context.getCacheDir().getAbsolutePath() );
                                webView.getSettings().setAllowFileAccess( true );
                                webView.getSettings().setAppCacheEnabled( true );
                                webView.getSettings().setJavaScriptEnabled( true );
                                webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

                                if ( !internetConnection.isConnected() ) { // loading offline
                                    webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
                                }

                                webView.loadUrl( "https://" + beaconListItems.getUrl().toString());
                                Toast.makeText(context, objectName.getText().toString() + " " + context.getString(R.string.loaded), Toast.LENGTH_SHORT).show();
//                                Uri url = Uri.parse("https://" + beaconListItems.getUrl().toString());
//                                Intent intentUrl = new Intent(Intent.ACTION_VIEW, url);
//                                context.startActivity(intentUrl);
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
        return null;
    }
}
