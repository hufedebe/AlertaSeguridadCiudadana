package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alertaciudadana.alertaseguridadciudadana.R;

public class SesionActivity extends AppCompatActivity {

    Button btn_ingresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);


        btn_ingresar = findViewById(R.id.btn_ingresar);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_home = new Intent(SesionActivity.this,MainActivity.class);
                startActivity(i_home);
            }
        });
    }
}
