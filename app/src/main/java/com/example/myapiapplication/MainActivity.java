package com.example.myapiapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.content.RestrictionsManager.RESULT_ERROR;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    //Initialize variables
    private GoogleMap mMap;
    //EditText editText;
    TextView textView1, textView2, textView3;
    //ListView listPlaces;
    //private Place place;

    private static final int PERMISSION_REQUEST_LOCATION = 0;
    private View mLayout;

    private TextView addressText;
    private Button LocationButton;
    private LocationRequest locationRequest;
    private Location mLastKnownLocation;

    private PlacesClient mPlacesClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int DEFAULT_ZOOM = 15;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private boolean mLocationPermissionGranted;

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mLayout = findViewById(R.id.main_layout);

        //Assign variable
        //editText = findViewById(R.id.edit_text);
        textView1 = findViewById(R.id.text_v1);
        textView2 = findViewById(R.id.text_v2);
        textView3 = findViewById(R.id.text_v3);

        //addressText = findViewById(R.id.address_text);
        LocationButton = findViewById(R.id.locationButton);
        // Set up the views list
        //listPlaces = findViewById(R.id.list_places);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //block level accuracy in order to get a list of places
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        //Initialize fusedLocationProviderClient
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        //Initialize places
        Places.initialize(getApplicationContext(), "AIzaSyDGu3GwM0PWcr9M9JDjE2oN4ckA6R2Jeik");

        //Set EditText non focusable
        //editText.setFocusable(false);
        // editText.setOnClickListener(new View.OnClickListener()

        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permission
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When permission granted
                    getLocation();
                } else {
                    //When permission denied
                    ActivityCompat.requestPermissions(MainActivity.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                    Snackbar.make(mLayout, "location_permission_denied", Snackbar.LENGTH_SHORT).show();
                    //turnOnGPS();
                }

//                //Initialize place field list
//                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
//
//                //Crate intent
//                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(MainActivity.this);
//
//                //Start activity result
//                startActivityForResult(intent, 100);

                //Get current location
                //getCurrentLocation();

            }
        });

    }

    private void getLocation() {
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location = task.getResult();
                if (location != null) {
                    try {
                        //Initialize geoCoder
                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());
                        //Initialize address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                        //Set latitude and longitude
                        textView1.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Latitude :</b><br></font>"
                                + addresses.get(0).getLatitude()
                        ));
                        //Set longitude
                        textView2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longitude :</b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));
                        //Set address
                        textView3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country Name :</b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        // BEGIN_INCLUDE(onRequestPermissionsResult)
//        if (requestCode == PERMISSION_REQUEST_LOCATION) {
//            // Request for location permission.
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //Checking for the GPS
//                if (isGPSEnabled()) {   //new
//                    // Permission has been granted. Start maps.
//                    Snackbar.make(mLayout, "location_permission_granted",
//                            Snackbar.LENGTH_SHORT).show();
//                    getCurrentLocation(); //new
//                    //startMaps();
//                } else {
//                    // Permission request was denied.
//                    Snackbar.make(mLayout, "location_permission_denied", Snackbar.LENGTH_SHORT).show();
//                    turnOnGPS();
//                }
//
//            } else {
//                // Permission request was denied.
//                Snackbar.make(mLayout, "location_permission_denied", Snackbar.LENGTH_SHORT).show();
//                turnOnGPS(); //new
//            }
//        }
//        // END_INCLUDE(onRequestPermissionsResult)
//    }

//    // Initialize the Map
//    private void startMaps() {
//        Intent intent = new Intent(this, MapsActivity.class);
//        startActivity(intent);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 100 && resultCode == RESULT_OK) {
//            //When success initialize place
//            Place place = Autocomplete.getPlaceFromIntent(data);
//            //Set address on EditText
//            //editText.setText(place.getAddress());
//            //Set locality name
//            addressText.setText(String.format("Locality Name: %s",place.getAddress()));
//            //textView1.setText(String.format("Locality Name : %s",place.getName()));
//            //Set latitude and longitude
//            textView2.setText(String.valueOf(place.getLatLng()));
//            //Get location id
//            textView3.setText(String.format("Location id : %s",place.getId()));
//        }else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//            //When error initialize status
//            Status status = Autocomplete.getStatusFromIntent(data);
//            //Display toast
//            Toast.makeText(getApplicationContext(), status.getStatusMessage(),Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 100) {
//            if (resultCode == RESULT_OK) {
//                getCurrentLocation();
//            } else if (resultCode == RESULT_ERROR) {
//                //When error initialize status
//                //Status status = Autocomplete.getStatusFromIntent(data);
//                //Display toast
//                //Toast.makeText(getApplicationContext(), status.getStatusMessage(),Toast.LENGTH_SHORT).show();
//                Snackbar.make(mLayout, "location_permission_denied", Snackbar.LENGTH_SHORT).show();   //μαλλον πιο σωστο απο το toast ???
//            }
//        }
//    }

//    //Get the current location
//    public static FindCurrentPlaceRequest.Builder builder (List<Place.Field> placeFields) {
//
//        return null;
//    }


//    private void getCurrentLocation() {
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                if (isGPSEnabled()) {
//
//                    LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
//                        @Override
//                        public void onLocationResult(@NonNull LocationResult locationResult) {
//                            super.onLocationResult(locationResult);
//
//                            LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
//
//                            if (locationResult != null && locationResult.getLocations().size() >0){
//
//                                int index = locationResult.getLocations().size() - 1;
//                                double latitude = locationResult.getLocations().get(index).getLatitude();
//                                double longitude = locationResult.getLocations().get(index).getLongitude();
//
//                                addressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
//                                //addressText.setText(String.format("Locality Name: %s",place.getAddress()));
//                                //addressText.setText("Currently at: "+ PlacesClient.findCurrentPlace() );
//                            }
//                        }
//                    }, Looper.getMainLooper());
//                } else {
//                    turnOnGPS();
//                }
//
//            } else {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            }
//        }
//    }
//
//    private void turnOnGPS() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
//                .checkLocationSettings(builder.build());
//
//        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//
//                try {
//                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    Toast.makeText(MainActivity.this, "GPS is already turned on", Toast.LENGTH_SHORT).show();
//
//                } catch (ApiException e) {
//
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(MainActivity.this, 2);
//                            } catch (IntentSender.SendIntentException ex) {
//                                ex.printStackTrace();
//                            }
//                            break;
//
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            //Device does not have location
//                            break;
//                    }
//                }
//            }
//        });
//
//    }
//
//    private boolean isGPSEnabled() {
//        LocationManager locationManager = null;
//        boolean isEnabled = false;
//
//        if (locationManager == null) {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        }
//
//        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        return isEnabled;
//
//    }

//    public FindCurrentPlaceRequest build () {
//        "https://maps.googleapis.com/maps/api/place/textsearch/json?location=latitude,longitude&radius=100&type=establishment_type&key=AIzaSyDGu3GwM0PWcr9M9JDjE2oN4ckA6R2Jeik"
//
//        return null;
//    }

//    //Filling the Places list with the appropriate information
//    private void fillPlacesList() {
//       // Set up an ArrayAdapter to convert likely places into TextViews to populate the ListView
//        ArrayAdapter<String> placesAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLikelyPlaceNames);
//        listPlaces.setAdapter(placesAdapter);
//        //listPlaces.setOnItemClickListener(listClickedHandler);
//    }
}