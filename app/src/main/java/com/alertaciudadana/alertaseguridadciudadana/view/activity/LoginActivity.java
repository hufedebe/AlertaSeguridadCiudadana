package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.view.model.IncidenteModel;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button btn_login, btn_registro;
    public static List<IncidenteModel> listIncidentes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        listIncidentes = new ArrayList<>();
        btn_login = findViewById(R.id.btn_login);
        btn_registro = findViewById(R.id.btn_registro);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_sesion = new Intent(LoginActivity.this,SesionActivity.class);
                startActivity(i_sesion);

            }
        });

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_registro = new Intent(LoginActivity.this,RegistroUsuarioActivity.class);
                startActivity(i_registro);
            }
        });



    }
}
