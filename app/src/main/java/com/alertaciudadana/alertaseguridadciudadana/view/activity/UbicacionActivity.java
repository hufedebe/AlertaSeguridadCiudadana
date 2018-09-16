package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class UbicacionActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnMapLoadedCallback{


    private Button btn_seguir;
    private Context mcontext;
    GoogleMap mGoogleMap;
    private Geocoder geocoder;
    private LatLng latlngcentro;
    private float  deslatitud, deslongitud;
    private LatLng userLocation;
    LocationManager locationManager;
    LocationListener locationListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        final String incidente= getIntent().getStringExtra("INCIDENTE");
        final String tipo = getIntent().getStringExtra("TIPO");
        final Integer idSubtipo = getIntent().getIntExtra("IDSUBTIPO",0);



        //DATOS DE LA PANTALLA
        final String descripcion= getIntent().getStringExtra("DESCRIPCION");
        final String numero= getIntent().getStringExtra("NUMERO");
        final String correo= getIntent().getStringExtra("CORREO");
        final String fecha= getIntent().getStringExtra("FECHA");
        final String hora= getIntent().getStringExtra("HORA");



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
                i_registro.putExtra("LATITUD", String.valueOf(latlngcentro.latitude));
                i_registro.putExtra("LONGITUD",String.valueOf(latlngcentro.longitude));
                i_registro.putExtra("TIPO",tipo);
                i_registro.putExtra("IDSUBTIPO",idSubtipo);



                i_registro.putExtra("DESCRIPCION",descripcion);
                i_registro.putExtra("NUMERO",numero);
                i_registro.putExtra("CORREO",correo);
                i_registro.putExtra("FECHA",fecha);
                i_registro.putExtra("HORA",hora);





                Log.d("Hugo", String.valueOf(latlngcentro.latitude));
                startActivity(i_registro);
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(mcontext);
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setOnMapLoadedCallback(this);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Toast.makeText(getActivity(), location.toString(), Toast.LENGTH_SHORT).show();
                //mGoogleMap.clear();
                userLocation = new LatLng(location.getLatitude(),location.getLongitude());





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
        if(deslatitud == 0.0 || deslongitud == 0.0){
            @SuppressLint("MissingPermission") Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 17));
        }else{
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(deslatitud, deslongitud), 17));
        }

    }

    @Override
    public void onCameraIdle() {
        latlngcentro = mGoogleMap.getCameraPosition().target;
        //cobertura = PolyUtil.containsLocation(latlngcentro, gpoligono.getPoints(), true);
        //if(cobertura) {

    }

    @Override
    public void onMapLoaded() {
        btn_seguir.setEnabled(true);
    }



}
