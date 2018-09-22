package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.LoginPost;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.LoginResponse;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.RegistroResponse;
import com.alertaciudadana.alertaseguridadciudadana.data.net.ApiAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SesionActivity extends AppCompatActivity {

    Button btn_ingresar;
    EditText correo, password;


    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);

        ConsultarActivity.tipo=0;


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            ;
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
        } else {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

            }
        }

        btn_ingresar = findViewById(R.id.btn_ingresar);
        correo = findViewById(R.id.editText_correo);
        password = findViewById(R.id.editText_contrasena);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(correo.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Ingrese su correo", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Ingrese una contraseña", Toast.LENGTH_LONG).show();
                }else{

                    LoginPost loginPost = new LoginPost();
                    loginPost.setEmail(correo.getText().toString());
                    loginPost.setPassword(password.getText().toString());

                    Call<LoginResponse> call = ApiAdapter.getApiService().postLogin(loginPost);
                    call.enqueue(new LoginCallback());

                }


            }
        });





    }


    class LoginCallback implements Callback<LoginResponse> {

        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            if(response.isSuccessful()){
                LoginResponse loginResponse = response.body();
                if (!TextUtils.isEmpty(loginResponse.getId())){

                    Toast.makeText(getApplicationContext(),"Inicio Correcto ", Toast.LENGTH_SHORT).show();
                    Intent register = new Intent(SesionActivity.this,PrincipalActivity.class);
                    register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(register);

                }else{

                    // Log.i("Retrofit", "post submitted to API." + response.body().toString());
                    try {
                        Toast.makeText(getApplicationContext(),response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }else{

                try {
                    Toast.makeText(getApplicationContext(),response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {

        }


    }

}
