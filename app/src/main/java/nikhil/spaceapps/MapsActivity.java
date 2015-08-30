package nikhil.spaceapps;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

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
import java.util.List;

import static nikhil.spaceapps.R.*;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    ArrayList<LatLng> latLngArrayList;

    TileOverlay mOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_maps);
        setUpMapIfNeeded();

        ActionBar barrrrrr = getActionBar();
        barrrrrr.setTitle("Meteor Shower");
        barrrrrr.setBackgroundDrawable(getDrawable(drawable.meteor_shower_3));

        latLngArrayList = new ArrayList<LatLng>();




//        mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));

    }




    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        // Fetch the Meteorite Data
        FetchMeteoriteDataTask meteoriteDataTask = new FetchMeteoriteDataTask();
        meteoriteDataTask.execute();

        // fetch the facility data
        NasaCenterTask nasaCenterTask = new NasaCenterTask();
        nasaCenterTask.execute();


    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == id.danger_report) {

//            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            double latitude ;
//            double longitude ;
//
//            if (location != null){
//                latitude = location.getLatitude();
//                longitude = location.getLongitude();
//            }
//
//            else {
//                Location getLastLocation = lm.getLastKnownLocation
//                        (LocationManager.PASSIVE_PROVIDER);
//                longitude = getLastLocation.getLongitude();
//                latitude = getLastLocation.getLatitude();
//            }

//            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            Criteria criteria = new Criteria();
//            String provider = locationManager.getBestProvider(criteria, true);

//            Location myLocation = locationManager.getLastKnownLocation(provider);

            // Get location
//            mMap.setMyLocationEnabled(true);
//            Location myLocation = mMap.getMyLocation();
//
//            double latitude = myLocation.getLatitude();
//            double longitude = myLocation.getLongitude();


//            Toast.makeText(this, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
//            Log.d("\n\n\n\n\nNOTOROUS", latitude + " " + longitude);

            DangerReportFragment fragment = new DangerReportFragment();
            fragment.context = getApplicationContext();
            fragment.latLngArrayList = latLngArrayList;
            fragment.show(getFragmentManager(), "");

//            // display the location fragment
//            EnterLocation enterLocation = new EnterLocation();
//            enterLocation.latLngArrayList = latLngArrayList;
//            enterLocation.show(getFragmentManager(), "");

        }

        return false;

    } // end of onOptionsItemsSelected




    /********************************************************************************
     *
     *  Auto-generated Google Maps Stuff
     *
      */

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

//        mMap.addMarker(new MarkerOptions().position(new LatLng(10, 10)).title("Hello"));
    }


    /***
     *
     *  End Auto-generated Google Maps Stuff
     *
     ******************************************************************************/



    // TODO  class to access the METEORITE STRIKE API

    public class FetchMeteoriteDataTask extends AsyncTask<Void, Void, Void> {

        private String rawJsonOutput;

        /*
        Asychronously connect to the api
         */

        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Contains raw JSON output as a string
            rawJsonOutput = null;

            try {

                final String urlString = "https://data.nasa.gov/resource/gh4g-9sfh.json";
                URL url = new URL(urlString);

                // create the request and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // read the input
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                // keep on reading
                while ((line = reader.readLine()) != null) {

                    // format json to have newlines
                    buffer.append(line + "\n");

                }// end of while

                rawJsonOutput = buffer.toString();
//                Log.d("\nRaw JSON Output: ", rawJsonOutput);

            } // end of try

            catch (IOException e) {
                e.printStackTrace();
            }

            finally {

                if (urlConnection != null)
                    urlConnection.disconnect();

                if (reader != null)
                    try {
                        reader.close();
                    }
                    catch (final IOException e) {
                        e.printStackTrace();
                    }


            }

            try {
                parseJSON();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        } // end of doInBackground


        /*
        * * Parse the JSON to get Latitudes and Longitudes
        */
        private void parseJSON () throws JSONException {

            JSONArray rootJSON = new JSONArray(rawJsonOutput);

            for (int i = 0; i < rootJSON.length(); i++) {

                JSONObject obj = rootJSON.getJSONObject(i);

                JSONObject geolocationObj = obj.getJSONObject("geolocation");
//
//                JSONObject JSONLat = geolocationObj.getJSONObject(2);
//                JSONObject JSONLong = geolocationObj.getJSONObject(3);

                String latitude = geolocationObj.getString("latitude");
                String longitude = geolocationObj.getString("longitude");

                Log.d("\n\n\n\nLatitude, Longitude", latitude + ", " + longitude);

                // add the lats and longs into the arraylist
                LatLng tempLatLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                latLngArrayList.add(tempLatLng);

            } // end of for loop


//            Log.d("\n\n\n\nRoot Array Test", rawJsonOutput);

//            plotOnMap();

        } // end of parseJSON


        @Override
        protected void onPostExecute(Void yolo) {
            plotOnMap();
//            Toast.makeText(getApplicationContext(), latLngArrayList.size(), Toast.LENGTH_SHORT).show();
            addHeatMap();
        }

        private void addHeatMap() {
//        List<LatLng> list = null;
//
//        // Get the data: latitude/longitude positions of police stations.
//        try {
//            list = readItems(R.raw.police_stations);
//        } catch (JSONException e) {
//            Toast.makeText(this, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
//        }

            int[] colors = {
                    Color.rgb(102, 225, 0), // green
                    Color.rgb(255, 0, 0),    // red
                    Color.BLUE
            };

            float[] startPoints = {
                    0.2f, 1f, 1.2f
            };

            // Create a heat map tile provider, passing it the latlngs of the police stations.
            Gradient gradient = new Gradient(colors, startPoints);

            HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                    .data(latLngArrayList)
                    .radius(50)
                    .gradient(gradient)
                    .build();

            // Add a tile overlay to the map, using the heat map tile provider.
            mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));


        } // addheatmap end


        /*
        Plot the latlngs on the map
         */

        private void plotOnMap() {

//            Toast.makeText(getApplicationContext(), latLngArrayList.size(), Toast.LENGTH_SHORT).show();

            for (LatLng l : latLngArrayList) {



                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(l);
                circleOptions.radius(5000);
                circleOptions.fillColor(0xffffffff);

                mMap.addCircle(circleOptions);
            }


//
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(1)));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(1)));

//            mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));/////////testetsetset

        } // end of plotOnMap


    } // end of fetch meteorite task


    // TODO class to access the NASA CENTER API

    public class NasaCenterTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // raw output
            String rawJsonOutput = null;

            try {

                URL url = new URL("https://data.nasa.gov/resource/gvk9-iz74.json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // read into inputstreamsteriniegrnawegtawet
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                rawJsonOutput = buffer.toString();

            } // end of try

            catch (IOException e) {
                e.printStackTrace();
            }

            finally {

                if (urlConnection != null)
                    urlConnection.disconnect();

                if (reader != null)
                    try {
                        reader.close();
                    }
                    catch (final IOException e) {
                        e.printStackTrace();
                    }
            }


            Log.d("NASA Facilities outputs: ", rawJsonOutput);

            return null;

        }// end of doinbaclkgorund




        @Override
        protected void onPostExecute(Void aVoid) {

        } // end of postexecute



    } // end of nasa class







} // end of class
