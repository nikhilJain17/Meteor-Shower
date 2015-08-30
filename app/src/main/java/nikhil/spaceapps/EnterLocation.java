package nikhil.spaceapps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Nick on 8/29/15.
 */
public class EnterLocation extends DialogFragment {

    // This latlng array is passed on to the dangerreportframgent
    ArrayList<LatLng> latLngArrayList;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//       requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().setContentView(R.layout.enterlocation);
//    }

    public EnterLocation() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setContentView(R.layout.enterlocation);
        getDialog().setTitle("Hello");

//        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle yolo) {

        getActivity().requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        Dialog dialog = builder.create();
//        dialog.setContentView(R.layout.enterlocation);
        return getDialog();

    } // end of oncreatedialog

} // end of class
