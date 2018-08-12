package com.alertaciudadana.alertaseguridadciudadana.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.RegistroActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private Button btn_registrar;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_home, container, false);
        return mView;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = mView.findViewById(R.id.map);

        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        Log.i("Test","Antes de inicializar botones");

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
    }
}
