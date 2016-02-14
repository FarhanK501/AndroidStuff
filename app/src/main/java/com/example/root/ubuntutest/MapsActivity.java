package com.example.root.ubuntutest;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity {


    /**
     * private variable to hold down latitude of the place
     */
    private double latitude = 27;

    /**
     * private variable to hold down longitude of the current place
     */
    private double longitude = 117;

    /**
     * Google Map object
     */
    private GoogleMap mMap;

    /**
     * name of the current place where marker is at.
     */
    private String currentPlace = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        onMapReady();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        //Button of find location
        Button findLocation = (Button) findViewById(R.id.findLocation);

        //tap listener of the find location
        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // edit text box for longitude input
                EditText longBox = (EditText) findViewById(R.id.longitude);

                // edit text box for latitude input
                EditText latBox = (EditText) findViewById(R.id.latitude);

                //text got from longitude input box
                final String longTxt = longBox.getText().toString();

                //text got from latitude input box
                final String  latTxt = latBox.getText().toString();

                //check whether if one of box is empty then show toast
                //that fields are empty else proceed
                if( !( latTxt.matches("")  || longTxt.matches("") ) ){

                    latitude = Double.valueOf(latTxt);
                    longitude = Double.valueOf(longTxt);

                    // getting place name on the basis of lat, long...
                    currentPlace = getPlaceName(latitude, longitude);

                    //call function
                    onMapReady();
                } else {
                    Toast.makeText(MapsActivity.this, "Please insert Longitude and Latitude", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * This method is responsible for get name of the places
     * on the basis of current longitude and latitude...
     * @param latitude
     * @param longitude
     * @return Place name as a String
     */
    private String getPlaceName(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        Log.e("Demo App", cityName + "  " + stateName);
        return cityName +" "+ stateName;

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady() {
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        if (mMap != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(currentPlace);
            mMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(1).build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        else if (mMap == null) {
            return;
        }
    }
}
