package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alertaciudadana.alertaseguridadciudadana.R;

public class BienestarActivity extends AppCompatActivity {


    private ImageView bienestarImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienestar);

        bienestarImage = findViewById(R.id.btn_bienestar);

        bienestarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_bienestar = new Intent(BienestarActivity.this,IncidenteActivity.class);
                BienestarActivity.this.startActivity(i_bienestar);
            }
        });

    }
}
