package com.alertaciudadana.alertaseguridadciudadana.view.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alertaciudadana.alertaseguridadciudadana.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallFragment extends Fragment {


    private ImageView img_ambulancia,img_bombero,img_municipalidad, img_policia,img_serenazgo,img_familia;

    public CallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img_ambulancia = view.findViewById(R.id.img_ambulancia);
        img_bombero = view.findViewById(R.id.img_bombero);
        img_municipalidad = view.findViewById(R.id.img_municipalidad);
        img_policia = view.findViewById(R.id.img_policia);
        img_serenazgo = view.findViewById(R.id.img_serenazgo);
        img_familia = view.findViewById(R.id.img_familia);


        img_serenazgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPermissionGranted()){
                    call_action("014481680");
                }

            }
        });

        img_policia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(isPermissionGranted()){
                        call_action("014481680");
                    }

            }
        });

        img_ambulancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPermissionGranted()){
                    call_action("012610502");
                }

            }
        });

        img_bombero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPermissionGranted()){
                    call_action("116");
                }

            }
        });

        img_municipalidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPermissionGranted()){
                    call_action("012410413");
                }

            }
        });

        img_familia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPermissionGranted()){
                    call_action("977245354");
                }

            }
        });







    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    //call_action();
                } else {
                     //Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @SuppressLint("MissingPermission")
    public void call_action(String number){

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        getActivity().startActivity(callIntent);
    }
}
