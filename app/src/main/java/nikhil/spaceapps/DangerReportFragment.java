package nikhil.spaceapps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.location.*;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Nick on 8/29/15.
 */
public class DangerReportFragment extends DialogFragment {

//    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//    double longitude = location.getLongitude();
//    double latitude = location.getLatitude();


    final double latitude = 40.709128;
    final double longitude = -74.0104544;
    Context context;

    // dangerous indexifiers
    int fiftyMileThreat = 0;
    int hundredMileThreat = 0;
    int fivehundredMileThreat = 0;
    int thousandMileThreat = 0;

    ArrayList<LatLng> latLngArrayList;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // get the user's location
//        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();

//        40.709128,	-74.0104544

        // make the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        int dangerousIndex = findDangerousIndex();

        builder.setTitle("Danger Report");
        builder.setMessage("Your location: " + latitude + ", " + longitude + "\n" +
                "50 Mile Threats: " + fiftyMileThreat + "\n" +
                "100 Mile Threats: " + hundredMileThreat + "\n" +
                "500 Mile Threats: " + fivehundredMileThreat + "\n" +
                "1000 Mile Threats: " + thousandMileThreat);



        return builder.create();

    } // end of createDialog

    private int findDangerousIndex() {

        int dangerousIndex;

        // counters
        fiftyMileThreat = 0;
        hundredMileThreat = 0;
        fivehundredMileThreat = 0;
        thousandMileThreat = 0;

        // Iterate through arraylist and get stuff done
        for (LatLng latLng : latLngArrayList) {

            double diffLat = Math.abs(latLng.latitude - latitude);
            double diffLong = Math.abs(latLng.longitude - longitude);

            if (diffLat <= 50 && diffLong <= 50)
                fiftyMileThreat++;

            else if (diffLat <= 100 && diffLong <= 100)
                hundredMileThreat++;

            else if (diffLat <= 500 && diffLong <= 500)
                fivehundredMileThreat++;

            else if (diffLat <= 1000 && diffLong <= 1000)
                thousandMileThreat++;

        }

        dangerousIndex = (int) (fiftyMileThreat * 3) + (int)(hundredMileThreat * 2) + (int)(fivehundredMileThreat * 1)
                + (int) (thousandMileThreat * 0.5);

        return dangerousIndex;

    }// end of findDangerousIndex






}// end of class
