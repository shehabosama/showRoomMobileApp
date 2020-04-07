package com.android.carview.AddShowRoomFragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.DepartmentResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddShowRoomFargment extends BaseFragment  implements AddShowroomContract.View,AddShowroomContract.Model.onFinishedListener {


    private Button btnGetMap,btnAddShowRoom;
    private Uri uri;
    private static final int PICK_FROM_GALLERY = 10;
    private MarkerOptions place1,place2;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    SupportMapFragment mapFragment;
    private double lat,lang;
    private CardView linearLayout;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private ProgressDialog progressDialog;
    private PresenterAddShowroom presenter;
    private Spinner spinner;
    int category = 0;
    private EditText editTextShowName;
    public AddShowRoomFargment() {
        // Required empty public constructor
    }

    public static AddShowRoomFargment newInstance (){
        return new AddShowRoomFargment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_show_room_fargment, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }


    @Override
    protected void initializeViews(View v) {

        setHasOptionsMenu(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
         mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        btnGetMap= v.findViewById(R.id.getMap);
        btnAddShowRoom = v.findViewById(R.id.btn_add_show_room);
        linearLayout =v.findViewById(R.id.lin);
        linearLayout.setVisibility(View.GONE);
        editTextShowName = v.findViewById(R.id.edit_text_show_name);
        progressDialog = new ProgressDialog(getActivity());
        presenter = new PresenterAddShowroom(this,this);
        List<String> category = new ArrayList<>();
        category.add(0,"Select Category");
        category.add(1,"Used");
        category.add(2,"New");
        spinner = v.findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,category);
        spinner.setAdapter(adapter);




    }

    public void getLocationInMap(){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(lat,lang))
                        .zoom(15)
                        .bearing(0)
                        .tilt(45)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(37.4219999, -122.0862462))
//                        .title("Spider Man")
//                        .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.used_car)));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat,lang))
                        .title("Iron Man")
                        .snippet("His Talent : Plenty of money"));

                Log.e(TAG, "onMapReady: "+lat+","+lang );
//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(37.3092293,-122.1136845))
//                        .title("Captain America"));

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    lat = latLng.latitude;
                    lang = latLng.longitude;

                    Message.message(getActivity(),"The Location is Selected");
                }
            });
            }
        });

    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.user_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setListeners() {

        btnGetMap.setOnClickListener(btnGetMapListener);
        spinner.setOnItemSelectedListener(spinnerCategoryListener);
        btnAddShowRoom.setOnClickListener(btnAddShowRoomListener);

    }

    private View.OnClickListener btnAddShowRoomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.performAddShowRoom(uri,getActivity(),
                    AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),
                    editTextShowName.getText().toString(),
                    String.valueOf(lat),String.valueOf(lang),String.valueOf(category));
        }
    };
    private AdapterView.OnItemSelectedListener spinnerCategoryListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            category = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private View.OnClickListener btnGetMapListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            linearLayout.setVisibility(View.VISIBLE);
            if (!checkPermissions()) {
                requestPermissions();
            } else {
                getLastLocation();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == PICK_FROM_GALLERY){
                uri = data.getData();
            }
        }
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.

            }
        }
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");


        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

//                            location=String.format(Locale.ENGLISH, "%s%f",
//                                    "",
//                                    mLastLocation.getLatitude())+String.format(Locale.ENGLISH, "%s,%f",
//                                    "",
//                                    mLastLocation.getLongitude());


                           // Message.message(getActivity(),"hello from " +String.valueOf(mLastLocation.getLatitude())+String.valueOf(mLastLocation.getLongitude()));

                            lat = mLastLocation.getLatitude();
                            lang = mLastLocation.getLongitude();
                            getLocationInMap();

                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());

                        }
                    }
                });
    }


    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
    }

    @Override
    public void onFailuer(Throwable t) {

        Log.e(TAG, "onFailuer: "+t.getLocalizedMessage() );
    }

    @Override
    public void loadDepartmentList(DepartmentResponse departmentResponse) {

    }

    @Override
    public void showProgress() {
        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void loadingSuccess() {

    }

    @Override
    public void loadingError() {

    }
}
