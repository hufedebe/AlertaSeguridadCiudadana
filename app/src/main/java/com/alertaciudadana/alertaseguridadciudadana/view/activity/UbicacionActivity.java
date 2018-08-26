package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionActivity extends AppCompatActivity implements OnMapReadyCallback{


    private Button btn_seguir;
    private Context mcontext;
    GoogleMap mGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        final String incidente= getIntent().getStringExtra("INCIDENTE");


            mcontext= getApplicationContext();
        btn_seguir = findViewById(R.id.btn_hecho);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapadestino);
        mapFragment.getMapAsync(this);

        btn_seguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_registro = new Intent(UbicacionActivity.this,IncidenteActivity.class);
                i_registro.putExtra("INCIDENTE", incidente.toString());
                startActivity(i_registro);
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(mcontext);
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.113301, -77.020770), 17));

    }
}
