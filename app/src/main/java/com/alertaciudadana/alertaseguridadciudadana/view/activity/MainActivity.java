package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.CallFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.HomeFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.MessageFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.ProfileFragment;
import com.alertaciudadana.alertaseguridadciudadana.view.fragment.StatisticsFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private CallFragment callFragment;
    private MessageFragment messageFragment;
    private ProfileFragment profileFragment;
    private StatisticsFragment statisticsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        callFragment = new CallFragment();
        messageFragment = new MessageFragment();
        profileFragment = new ProfileFragment();
        statisticsFragment = new StatisticsFragment();

        setFragment(homeFragment);

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
        mMainNav.setSelectedItemId(R.id.nav_home);
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();

    }
}
