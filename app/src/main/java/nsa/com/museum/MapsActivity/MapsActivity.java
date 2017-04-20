package nsa.com.museum.MapsActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nsa.com.museum.R;

/**===MAPS FUNCTIONALITY===
 * Adapted using combination of Android Docs, Google Developer and Stack Overflow at the following links.
 * Adding a map with a Marker: https://developers.google.com/maps/documentation/android-api/map-with-marker
 * Getting the Last Known Location: https://developer.android.com/training/location/retrieve-current.html
 * Changing Location Settings: https://developer.android.com/training/location/change-location-settings.html
 * Receiving Location Updates: https://developer.android.com/training/location/receive-location-updates.html
 * Current location and Google Maps: http://stackoverflow.com/questions/34582370/how-can-i-show-current-location-on-a-google-map-on-android-marshmallow/34582595#3458259
 */

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    GoogleMap googleMap;
    SupportMapFragment supportMapFragment;
    LocationRequest locationRequest;
    GoogleApiClient googleApiClient;
    Location lastLocation;
    Marker currentLocationMarker;
    LatLng latLng;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        // This method stops location updates when Activity is no longer active
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    // This method is only called when the map is ready.
    @Override
    public void onMapReady(GoogleMap cGoogleMap) {
        this.googleMap =cGoogleMap;

        // Initialises the Google Play Services.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Checks if permission has already been granted.
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();

                // Builds the map and all UI features.
                this.googleMap.setMyLocationEnabled(true);
                UiSettings uiSettings = this.googleMap.getUiSettings();
                uiSettings.setAllGesturesEnabled(true);
                uiSettings.setMyLocationButtonEnabled(true);
                uiSettings.setZoomControlsEnabled(true);
            }
            // Asks for permission if it has not been granted.
            else {
                checkLocationPermission();
            }
        }
        else {
            // Builds the map and all UI features.
            buildGoogleApiClient();
            this.googleMap.setMyLocationEnabled(true);
            UiSettings uiSettings = this.googleMap.getUiSettings();
            uiSettings.setAllGesturesEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);
            uiSettings.setZoomControlsEnabled(true);
        }
    }

    /** This method builds and connects the Google API Client  between the
     *  user's device and the Google services required for this app. I.E.
     *  Google's Location Services.
     */
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    /** This method is initialised when the Google API Client has been connected.
     *  It creates a new location request to Google Play Services and the data
     *  provided is stored in the bundle.
     */
    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    // Generated method from implementing ConnectionCallbacks
    @Override
    public void onConnectionSuspended(int i) {}

    // Generated method from implementing ConnectionCallbacks
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    // Called when the user's location has changed.
    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        // Removes current marker if it exists as it is no longer accurate.
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        //Places a new current location marker in the new position.
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You are here");
        currentLocationMarker = googleMap.addMarker(markerOptions);

        // Triggers the map to focus on the new location.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

        /** Creates a new string builder passing it the results of the
         *  string builder method. This is used to find information about
         *  museums in the local area.
         */
        StringBuilder stringBuilder = new StringBuilder(sbMethod());
        PlacesTask placesTask = new PlacesTask();
        placesTask.execute(stringBuilder.toString());
    }

    // Checks for location permissions required to run this section of the app.
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 200;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                /** The code below creates an alert to the user explaining the need
                 *  for location permissions should they refuse, and then asks them
                 *  for the permission again.
                 */
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Required")
                        .setMessage("This app needs the Location permission to find your current location, please accept or this functionality will not work.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No alert is needed so permission is requested.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    // This method deals with all potential outcomes of requesting permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted meaning maps can be used.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission was denied, so the user is presented with a toast. Maps will not work.
                    Toast.makeText(this, "Sorry, permission denied.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    /** This is the string builder method used to create a filter for nearby
     *  places to the current location. The below filter defines that all
     *  museums within 20km of the current location must be shown on the map.
     */
    public StringBuilder sbMethod() throws SecurityException {
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + latitude + "," + longitude);
        sb.append("&radius=20000");
        sb.append("&types=" + "museum");
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCaVt0zmzEyVb9-02WV2Yyrjr9KDxRn7Nw");

        Log.d("Map", "url: " + sb.toString());

        return sb;
    }

    /** All below methods until end of file referenced from:
     * http://stackoverflow.com/questions/33971717/mapactivity-query-for-nearest-hospital-restaurant-not-working?noredirect=1&lq=1
     */
    private class PlacesTask extends AsyncTask<String, Integer, String>
    {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParserTask
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException
    {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            Place_JSON placeJson = new Place_JSON();

            try {
                jObject = new JSONObject(jsonData[0]);

                places = placeJson.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            Log.d("Map", "list size: " + list.size());
            // Clears all the existing markers;
            //googleMap.clear();

            for (int i = 0; i < list.size(); i++) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);


                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));

                // Getting name
                String name = hmPlace.get("place_name");

                Log.d("Map", "place: " + name);

                // Getting vicinity
                String vicinity = hmPlace.get("vicinity");

                latLng = new LatLng(lat, lng);

                // Setting the position for the marker
                markerOptions.position(latLng);

                markerOptions.title(name + " : " + vicinity);

                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                // Placing a marker on the touched position
                Marker m = googleMap.addMarker(markerOptions);

                // When marker is clicked, it takes it to google maps page with all the info related to it
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Uri uri = Uri.parse("https://www.google.co.uk/maps?q=newport+museum+and+art+gallery&um=1&ie=UTF-8&sa=X&ved=0ahUKEwiN1bzutorTAhWKthoKHWfPCOkQ_AUICCgB");
                        Intent o = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(o);
                    }
                });


            }
        }
    }
    public class Place_JSON {

        // Receives a JSONObject and returns a list
        public List<HashMap<String, String>> parse(JSONObject jObject) {

            JSONArray jPlaces = null;
            try {
                // Retrieves all the elements in the 'places' array */
                jPlaces = jObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /** Invoking getPlaces with the array of json object
             * where each json object represent a place
             */
            return getPlaces(jPlaces);
        }

        private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
            int placesCount = jPlaces.length();
            List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> place = null;

            // Taking each place, parses and adds to list object */
            for (int i = 0; i < placesCount; i++) {
                try {
                    // Call getPlace with place JSON object to parse the place */
                    place = getPlace((JSONObject) jPlaces.get(i));
                    placesList.add(place);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }

        //Parsing the Place JSON object
        private HashMap<String, String> getPlace(JSONObject jPlace)
        {

            HashMap<String, String> place = new HashMap<String, String>();
            String placeName = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";

            try {
                // Extracting Place name, if available
                if (!jPlace.isNull("name")) {
                    placeName = jPlace.getString("name");
                }

                // Extracting Place Vicinity, if available
                if (!jPlace.isNull("vicinity")) {
                    vicinity = jPlace.getString("vicinity");
                }

                latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = jPlace.getString("reference");

                place.put("place_name", placeName);
                place.put("vicinity", vicinity);
                place.put("lat", latitude);
                place.put("lng", longitude);
                place.put("reference", reference);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return place;
        }
    }
}