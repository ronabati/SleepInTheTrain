package com.example.xiaoyi.sleepinthetrain;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener,LocationListener,GoogleApiClient.ConnectionCallbacks ,
GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1999;

    private GoogleMap mMap;
    public static GoogleApiClient client;
    public static Circle circle;
    public static Location location;
    public static final String TAG = "MapFragment";
    private LocationRequest mLocationRequest = null;
    private Location mCurrentLocation = null;
    private String seachText = null;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    private static final int MINIMUM_TIME = 10000;
    private static final int MINIMUM_DISTANCE = 50;

    private String mProviderName;
    private LocationManager mlocationManager;
    private LocationListener mLocationListener;
    private static final int REQUEST_CODE_LOCATION = 2;
    private ArcMenu mArcMenu;
    protected GoogleApiClient myGoogleApiClient;
    private EditText mAutocompleteView;
    private RecyclerView myRecyclerView;
    private LinearLayoutManager myLinearLayoutManager;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;

    private TextView mTxtMapPlace;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    LatLng current_location = null;
    LocationManager locationManager = null;



    private static final LatLngBounds BOUNDS_SYD = new LatLngBounds(
            new LatLng(-0, 0), new LatLng(0, 0));



//    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000; //The desired interval for location updates. Inexact. Updates may be more or less frequent.
//    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
//            UPDATE_INTERVAL_IN_MILLISECONDS / 2; //The fastest rate for active location updates. Exact. Updates will never be more frequent than this value.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_maps);
        mArcMenu = (ArcMenu)findViewById(R.id.id_menu);

        mTxtMapPlace = (TextView) findViewById(R.id.tv_placing);
        mTxtMapPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutocompleteActivity();
            }
        });

        mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        
        initEvent();

//        mAutocompleteView =(EditText) findViewById(R.id.place_autocomplete);
//        mAutoCompleteAdapter =  new PlacesAutoCompleteAdapter(this, R.layout.search_row,
//                myGoogleApiClient, BOUNDS_SYD,null);
//        myRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//        myLinearLayoutManager = new LinearLayoutManager(this);
//        myRecyclerView.setLayoutManager(myLinearLayoutManager);
//        myRecyclerView.setAdapter(mAutoCompleteAdapter);

//        mAutocompleteView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (!s.toString().equals("") && myGoogleApiClient.isConnected()) {
//                    mAutoCompleteAdapter.getFilter().filter(s.toString());
//                }else if(!myGoogleApiClient.isConnected()){Toast.makeText(getApplicationContext(),Constants.API_NOT_CONNECTED,Toast.LENGTH_SHORT).show();
//                    Log.e(Constants.PlacesTag,Constants.API_NOT_CONNECTED);
//
//                }
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });

//        myRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,new RecyclerItemClickListener.OnItemClickListener(){
//            @Override
//        public void onItemClick(View view,int position){
//                final PlacesAutoCompleteAdapter.PlaceAutocomplete item = mAutoCompleteAdapter.getItem(position);
//                final String placed = String.valueOf(item.placeId);
//
//                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(myGoogleApiClient,placed);
//
//                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
//                    @Override
//                    public void onResult(PlaceBuffer places) {
//                        if (places.getCount()==1){
//                            Toast.makeText(getApplicationContext(),String.valueOf(places.get(0).getLatLng()),Toast.LENGTH_SHORT).show();
//                        }else {
//
//                        }
//                    }
//                });
//            }
//
//        }));




    }

    //click method
    private void initEvent() {
        mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                switch (pos) {
                    case 1:
                    Intent intent = new Intent(MapsActivity.this,SettingActivity.class);
                    startActivity(intent);
                        break;
                    case 2:
                        openAutocompleteActivity();

                }

            }
        });



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


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        requestPermissions(LOCATION_PERMS, 100);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, this);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                current_location = new LatLng(location.getLatitude(), location.getLongitude());
            }
            Log.e("activity", "*********" + current_location.latitude + "***" + current_location.longitude);
//        int x = requestCode;
            if (current_location != null) {


//         Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-33.8674870, 151.2069900);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.8674870, 151.2069900), 18));
            }
        }
        mMap.setMyLocationEnabled(true);
    }



    protected synchronized void buildGoogleApiClient(){
        myGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();

        myGoogleApiClient.connect();

    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        float[] distance = new float[50];
        Location.distanceBetween(current_location.latitude, current_location.longitude, latLng.latitude, latLng.longitude,distance);
        Log.e("activity", String.valueOf(distance[0]) + "*********************");
        Toast.makeText(this, "The distance is" + distance[0] / 1000 + "km", Toast.LENGTH_LONG).show();

    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);

            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + place.getName());


                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title("Marker in" + place.getAddress()));

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(place.getLatLng());
                mMap.animateCamera(cameraUpdate);

                // Format the place's details and display them in the TextView.
//                mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
//                        place.getId(), place.getAddress(), place.getPhoneNumber(),
//                        place.getWebsiteUri()));

                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
//                if (!TextUtils.isEmpty(attributions)) {
//                    mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
//                } else {
//                    mPlaceAttribution.setText("");
//                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }
}

