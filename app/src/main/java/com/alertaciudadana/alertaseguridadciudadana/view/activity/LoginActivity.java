package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alertaciudadana.alertaseguridadciudadana.R;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_login = findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_sesion = new Intent(LoginActivity.this,SesionActivity.class);
                startActivity(i_sesion);

            }
        });
    }
}
