package nikhil.spaceapps;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        // Fetch the Meteorite Data
        FetchMeteoriteDataTask meteoriteDataTask = new FetchMeteoriteDataTask();
        meteoriteDataTask.execute();

    }


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
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
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
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    /***
     *
     *  End Auto-generated Google Maps Stuff
     *
     ******************************************************************************/



    // TODO Anonymous class to access the METEORITE STRIKE API

    public class FetchMeteoriteDataTask extends AsyncTask<Void, Void, Void> {

        private String rawJsonOutput;

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

            }


//            Log.d("\n\n\n\nRoot Array Test", rawJsonOutput);


        } // end of parseJSON



    } // end of fetch meteorite task









} // end of class
