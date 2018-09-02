package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.CallFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.HomeFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.MessageFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.ProfileFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.StatisticsFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.model.IncidenteModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private CallFragment callFragment;
    private MessageFragment messageFragment;
    private ProfileFragment profileFragment;
    private StatisticsFragment statisticsFragment;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Integer fragmentId = getIntent().getIntExtra("fragment",0);
        Log.i("Hugo",fragmentId.toString());

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        callFragment = new CallFragment();
        messageFragment = new MessageFragment();
        profileFragment = new ProfileFragment();
        statisticsFragment = new StatisticsFragment();

        if(fragmentId==1){
            setFragment(homeFragment);
            mMainNav.setSelectedItemId(R.id.nav_home);
        }else if(fragmentId==2){
            setFragment(callFragment);
            mMainNav.setSelectedItemId(R.id.nav_call);
        }else if(fragmentId==3){
            setFragment(messageFragment);
            mMainNav.setSelectedItemId(R.id.nav_sms);
        }else if(fragmentId==4){
            setFragment(statisticsFragment);
            mMainNav.setSelectedItemId(R.id.nav_statistics);
        }else if(fragmentId==5){
            setFragment(profileFragment);
            mMainNav.setSelectedItemId(R.id.nav_user);
        }else{
            setFragment(homeFragment);
            mMainNav.setSelectedItemId(R.id.nav_home);
        }


       // setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home :
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_user :
                        setFragment(profileFragment);
                        return true;

                    case R.id.nav_sms:
                        setFragment(messageFragment);
                        return true;

                    case R.id.nav_call:
                        setFragment(callFragment);
                        return true;

                    case R.id.nav_statistics:
                        setFragment(statisticsFragment);
                        return true;

                    default:
                        return false;

                }


            }
        });

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();

    }


}
