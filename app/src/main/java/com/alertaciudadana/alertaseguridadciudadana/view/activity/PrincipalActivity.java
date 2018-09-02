package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alertaciudadana.alertaseguridadciudadana.R;

public class PrincipalActivity extends AppCompatActivity {


    ImageView img_mapa, img_marcacion, img_chat,img_reportes, img_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        img_mapa = findViewById(R.id.img_mapacrimen);
        img_marcacion= findViewById(R.id.img_marcacion);
        img_chat = findViewById(R.id.img_chat);
        img_reportes = findViewById(R.id.img_reporte);
        img_perfil = findViewById(R.id.img_perfil);

        img_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_home = new Intent(PrincipalActivity.this,MainActivity.class);
                i_home.putExtra("fragment",1);
                startActivity(i_home);
            }
        });


        img_marcacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_home = new Intent(PrincipalActivity.this,MainActivity.class);
                i_home.putExtra("fragment",2);
                startActivity(i_home);
            }
        });

        img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_home = new Intent(PrincipalActivity.this,MainActivity.class);
                i_home.putExtra("fragment",3);
                startActivity(i_home);
            }
        });

        img_reportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_home = new Intent(PrincipalActivity.this,MainActivity.class);
                i_home.putExtra("fragment",4);
                startActivity(i_home);
            }
        });

        img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_home = new Intent(PrincipalActivity.this,MainActivity.class);
                i_home.putExtra("fragment",5);
                startActivity(i_home);
            }
        });

    }
}
