package com.alertaciudadana.alertaseguridadciudadana.view.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.RegistroActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    TextView incidente;
    LocationManager locationManager;
    LocationListener locationListener;
    private BottomSheetBehavior bottomSheetBehavior;
    private NestedScrollView bottomSheet;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }


        }
    }



    View mView;
    private Button btn_registrar;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        return mView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = mView.findViewById(R.id.map);
        bottomSheet = mView.findViewById(R.id.bottom_sheet);
        incidente= bottomSheet.findViewById(R.id.detail_name);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               // Toast.makeText(getActivity(), location.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }


        };

        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }else{
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String [] {Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }

        //Obtener botones
        btn_registrar= getView().findViewById(R.id.btn_registrar);





        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test","Entrando");
                Intent iRegistro = new Intent(getActivity(), RegistroActivity.class);
                startActivity(iRegistro);
            }
        });





    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.113254, -77.023343), 15));




        LatLng pandillaje2 = new LatLng(-12.113254, -77.023343);
        LatLng droga = new LatLng(-12.113480, -77.022936);
        LatLng robo = new LatLng(-12.112653, -77.018377);
        LatLng droga2 = new LatLng(-12.111193, -77.023869);



        googleMap.addMarker(new MarkerOptions().position(pandillaje2)
                .title("Pandillaje")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pandillaje)));

        googleMap.addMarker(new MarkerOptions().position(droga)
                .title("Venta de Droga")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_droga)));

        googleMap.addMarker(new MarkerOptions().position(robo)
                .title("Robo")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_robo_2)));


        googleMap.addMarker(new MarkerOptions().position(droga2)
                .title("Venta de Droga")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_droga)));

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.112304, -77.007116), 10));

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                updateBottomSheetContent(marker);
                return true;
            }
        });
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });




    }

    private void updateBottomSheetContent(Marker marker) {
        TextView name =  bottomSheet.findViewById(R.id.detail_name);
        Log.i("Hugo",marker.getTitle().toString());
        incidente.setText(marker.getTitle().toString());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}
