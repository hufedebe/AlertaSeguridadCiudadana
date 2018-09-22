package com.alertaciudadana.alertaseguridadciudadana.view.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.Incidente;
import com.alertaciudadana.alertaseguridadciudadana.data.net.ApiAdapter;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.BienestarActivity;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.ConsultarActivity;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.IncidenteActivity;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.LoginActivity;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.MainActivity;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.PrincipalActivity;
import com.alertaciudadana.alertaseguridadciudadana.view.activity.RegistroActivity;
import com.alertaciudadana.alertaseguridadciudadana.view.model.IncidenteModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    TextView incidente;
    TextView txt_fecha;
    TextView txt_hora;
    TextView txt_descripcion;
    ImageButton img_accidente;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLocation;
    Location lastKnownLocation;
    private BottomSheetBehavior bottomSheetBehavior;
    private NestedScrollView bottomSheet;
    private List<IncidenteModel> markerList;
    View mView;
    private Button btn_registrar;
    private Button btn_alertaRapida;
    private Button btn_consultarAlerta;

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

        markerList= new ArrayList<IncidenteModel>();
        mMapView = mView.findViewById(R.id.map);
        bottomSheet = mView.findViewById(R.id.bottom_sheet);
        incidente = bottomSheet.findViewById(R.id.detail_name);
        txt_descripcion = bottomSheet.findViewById(R.id.txt_descripcion);
        txt_fecha = bottomSheet.findViewById(R.id.txt_fecha);
        txt_hora = bottomSheet.findViewById(R.id.txt_hora);
        img_accidente = bottomSheet.findViewById(R.id.img_accidente);



        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(300);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();

            mMapView.getMapAsync(this);
            View locationButton = ((View) mMapView.getRootView().findViewById(Integer.parseInt("1")).
                    getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rlp.setMargins(0, 0, 30, 500);
        }




        if(PrincipalActivity.alerta){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enviarAlerta();
                }
            }, 1000);


        }

        //Obtener botones
        btn_registrar = getView().findViewById(R.id.btn_registrar);
        btn_alertaRapida = getView().findViewById(R.id.btn_alertaRapida);
        btn_consultarAlerta= getView().findViewById(R.id.btn_consultarAlerta);


        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", "Entrando");
                Intent iRegistro = new Intent(getActivity(), RegistroActivity.class);
                startActivity(iRegistro);
            }
        });

        btn_consultarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Test", "Entrando");
                Intent iCOnsulta = new Intent(getActivity(), ConsultarActivity.class);
                startActivity(iCOnsulta);
            }
        });



        btn_alertaRapida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userLocation= new LatLng(getLastKnownLocation().getLatitude(),getLastKnownLocation().getLongitude());


                if (userLocation != null) {
                    mGoogleMap.addMarker(new MarkerOptions().position(userLocation)
                            .title("Alerta")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_alerta_user)));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i_alerta = new Intent(getActivity(), IncidenteActivity.class);
                            i_alerta.putExtra("INCIDENTE", "otro");
                            i_alerta.putExtra("TIPO", "A");
                            i_alerta.putExtra("LATITUD", String.valueOf(userLocation.latitude));
                            i_alerta.putExtra("LONGITUD", String.valueOf(userLocation.longitude));
                            startActivity(i_alerta);
                        }
                    }, 1000);

                } else {
                    Toast.makeText(getContext(),"Localizandolo", Toast.LENGTH_SHORT).show();
                    Intent iRegistro = new Intent(getActivity(), MainActivity.class);
                    startActivity(iRegistro);
                }



            }
        });


    }

    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = locationManager.getLastKnownLocation(provider);


            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }



    private void enviarAlerta() {

        userLocation= new LatLng(getLastKnownLocation().getLatitude(),getLastKnownLocation().getLongitude());
        if (userLocation != null) {
            mGoogleMap.addMarker(new MarkerOptions().position(userLocation)
                    .title("Alerta")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_alerta_user)));
            PrincipalActivity.alerta=false;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i_alerta = new Intent(getActivity(), IncidenteActivity.class);
                    i_alerta.putExtra("INCIDENTE", "otro");
                    i_alerta.putExtra("TIPO", "A");
                    i_alerta.putExtra("LATITUD", String.valueOf(userLocation.latitude));
                    i_alerta.putExtra("LONGITUD", String.valueOf(userLocation.longitude));
                    startActivity(i_alerta);
                }
            }, 1000);

        } else {
            Log.i("Test", "Entrando");
            Intent iRegistro = new Intent(getActivity(), MainActivity.class);
            startActivity(iRegistro);

        }



    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        googleMap.setMyLocationEnabled(true);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.113254, -77.023343), 15));


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }else{
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String [] {Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{
                if(locationManager!=null){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()),16));
                }


            }
        }


        /*
        LatLng pandillaje2 = new LatLng(-12.113254, -77.023343);
        LatLng droga = new LatLng(-12.113480, -77.022936);
        LatLng robo = new LatLng(-12.112653, -77.018377);
        LatLng droga2 = new LatLng(-12.111193, -77.023869);
        */

  /*
        markerList = MainActivity.listIncidentes;

        for(IncidenteModel incidenteModel: markerList){

           LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
            googleMap.addMarker(new MarkerOptions().position(position)
                    .title(incidenteModel.getSubtipo().toString()));
        }

*/

        //markerList.clear();
        traerIncidentes();

        //markerList = LoginActivity.listIncidentes;

        for(IncidenteModel incidenteModel: markerList){


            if(incidenteModel.getTipo()!=null){

                if(ConsultarActivity.tipo==0){
                    if(incidenteModel.getTipo().equals("S")){
                        LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                        mGoogleMap.addMarker(new MarkerOptions().position(position)
                                .title(incidenteModel.getId()));
                    }else if(incidenteModel.getTipo().equals("B")){
                        LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                        mGoogleMap.addMarker(new MarkerOptions().position(position)
                                .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }else if(incidenteModel.getTipo().equals("A")){
                        LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                        mGoogleMap.addMarker(new MarkerOptions().position(position)
                                .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_alerta_user)));

                    }
                }else if(ConsultarActivity.tipo==1){

                    if(ConsultarActivity.subtipo==0){
                        if(incidenteModel.getTipo().equals("B")){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==1){
                        if(incidenteModel.getId_subtipo()==1){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==2){
                        if(incidenteModel.getId_subtipo()==2){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==3){
                        if(incidenteModel.getId_subtipo()==3){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==4){
                        if(incidenteModel.getId_subtipo()==4){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==5){
                        if(incidenteModel.getId_subtipo()==5){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }


                }else if(ConsultarActivity.tipo==2){



                    if(ConsultarActivity.subtipo==0){
                        if(incidenteModel.getTipo().equals("S")){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()));
                        }

                    }else if(ConsultarActivity.subtipo==1){
                        if(incidenteModel.getId_subtipo()==1){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==2){
                        if(incidenteModel.getId_subtipo()==2){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==3){
                        if(incidenteModel.getId_subtipo()==3){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==4){
                        if(incidenteModel.getId_subtipo()==4){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==5){
                        if(incidenteModel.getId_subtipo()==5){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==6){
                        if(incidenteModel.getId_subtipo()==6){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==7){
                        if(incidenteModel.getId_subtipo()==7){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }



                }else if(ConsultarActivity.tipo==3){
                    if(incidenteModel.getTipo().equals("A")){
                        LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                        mGoogleMap.addMarker(new MarkerOptions().position(position)
                                .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_alerta_user)));

                    }
                }

            }

        }


        /*
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

                */

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.112304, -77.007116), 10));

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                IncidenteModel currentMarker = new IncidenteModel();
                //currentMarker= markerList.get(Integer.parseInt(marker.getTitle())-1);


                for(IncidenteModel incidenteModel: markerList){
                    if(marker.getTitle().equals(incidenteModel.getId())){
                        currentMarker=incidenteModel;
                    }


                }
               // currentMarker= markerList.get(marker.getTitle());
                updateBottomSheetContent(currentMarker);
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

    private void traerIncidentes() {
        Call<List<Incidente>> call = ApiAdapter.getApiService().getIncidenteList();
        call.enqueue(new ListIncidentesCallback());
    }


    class ListIncidentesCallback implements Callback<List<Incidente>> {
        @Override
        public void onResponse(Call<List<Incidente>> call, Response<List<Incidente>> response) {
            if (response.isSuccessful()){
                //ListarEventosResponse eventosResponse = response.body();
                markerList.clear();
                List<Incidente> arrayList = new ArrayList<>();
                arrayList = response.body();
                poblarIncidentes(arrayList);
                //poblarListaTalleres(talleresResponse.getTalleres());

            }else{
                //Toast.makeText(getApplicationContext(),"Error en el formato ", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<Incidente>> call, Throwable t) {

        }
    }

    private void poblarIncidentes(List<Incidente> array) {


        for(Incidente incidenteEntity : array) {

            Log.i("Hugo",incidenteEntity.getId());

            IncidenteModel incidenteModel = new IncidenteModel();

            incidenteModel.setId(incidenteEntity.getId());
            incidenteModel.setId_subtipo(incidenteEntity.getSubTipo());
            incidenteModel.setTipo(incidenteEntity.getTipo());
            incidenteModel.setSubtipo(incidenteEntity.getNombreIncidente());

            incidenteModel.setDescripcion(incidenteEntity.getDescripcion());
            incidenteModel.setNumero(incidenteEntity.getCelular());
            incidenteModel.setCorreo(incidenteEntity.getEmail());
            incidenteModel.setFecha(incidenteEntity.getFecha());
            incidenteModel.setHora(incidenteEntity.getHora());
            incidenteModel.setLongitud(String.valueOf(incidenteEntity.getLongitud()));
            incidenteModel.setLatitude(String.valueOf(incidenteEntity.getLatitud()));
            markerList.add(incidenteModel);

        }

        poblarMapaIncidentes();

    }


    private void poblarMapaIncidentes(){
        Log.i("Hugo", String.valueOf(markerList.size()));
        for(IncidenteModel incidenteModel: markerList){
            Log.i("Hugo",incidenteModel.getTipo());
            Log.i("Hugo", String.valueOf(ConsultarActivity.tipo));
            if(incidenteModel.getTipo()!=null){

                if(ConsultarActivity.tipo==0){
                    if(incidenteModel.getTipo().equals("S")){
                        LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                        mGoogleMap.addMarker(new MarkerOptions().position(position)
                                .title(incidenteModel.getId()));
                    }else if(incidenteModel.getTipo().equals("B")){
                        LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                        mGoogleMap.addMarker(new MarkerOptions().position(position)
                                .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }else if(incidenteModel.getTipo().equals("A")){
                        LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                        mGoogleMap.addMarker(new MarkerOptions().position(position)
                                .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_alerta_user)));

                    }
                }else if(ConsultarActivity.tipo==1){

                    if(ConsultarActivity.subtipo==0){
                        if(incidenteModel.getTipo().equals("B")){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==1){
                        if(incidenteModel.getId_subtipo()==1){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==2){
                        if(incidenteModel.getId_subtipo()==2){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==3){
                        if(incidenteModel.getId_subtipo()==3){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==4){
                        if(incidenteModel.getId_subtipo()==4){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==5){
                        if(incidenteModel.getId_subtipo()==5){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }


                }else if(ConsultarActivity.tipo==2){



                    if(ConsultarActivity.subtipo==0){
                        if(incidenteModel.getTipo().equals("S")){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()));
                        }

                    }else if(ConsultarActivity.subtipo==1){
                        if(incidenteModel.getId_subtipo()==1){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==2){
                        if(incidenteModel.getId_subtipo()==2){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==3){
                        if(incidenteModel.getId_subtipo()==3){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==4){
                        if(incidenteModel.getId_subtipo()==4){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==5){
                        if(incidenteModel.getId_subtipo()==5){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==6){
                        if(incidenteModel.getId_subtipo()==6){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }else if(ConsultarActivity.subtipo==7){
                        if(incidenteModel.getId_subtipo()==7){
                            LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                            mGoogleMap.addMarker(new MarkerOptions().position(position)
                                    .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }
                    }



                }else if(ConsultarActivity.tipo==3){
                    if(incidenteModel.getTipo().equals("A")){
                        LatLng position = new LatLng(Float.parseFloat(incidenteModel.getLatitude()),Float.parseFloat(incidenteModel.getLongitud()));
                        mGoogleMap.addMarker(new MarkerOptions().position(position)
                                .title(incidenteModel.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_alerta_user)));

                    }
                }

            }

        }


    }



    @Override
    public void onResume() {
        super.onResume();
        Log.d("Hugo","Entrando");


    }

    private void updateBottomSheetContent(IncidenteModel currentMarker) {
        //IncidenteModel currentMarker = new IncidenteModel();
        Log.d("Hugo",currentMarker.getSubtipo());
        //currentMarker= LoginActivity.listIncidentes.get(Integer.parseInt(marker.getTitle()));

        //Log.i("Hugo",marker.getTitle().toString());
       // incidente.setText(marker.getTitle().toString());
        txt_descripcion.setText(currentMarker.getDescripcion());
        txt_hora.setText(currentMarker.getHora());
        txt_fecha.setText(currentMarker.getFecha());
        incidente.setText(currentMarker.getSubtipo());
        img_accidente.setImageBitmap(currentMarker.getImg_incidente());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


}
