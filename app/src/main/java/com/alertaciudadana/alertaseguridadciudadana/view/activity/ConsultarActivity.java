package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.alertaciudadana.alertaseguridadciudadana.R;

import java.util.ArrayList;
import java.util.List;

public class ConsultarActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {


    Spinner sp_incidente, sp_subtipo;
    Button btn_buscar;
    public static Integer tipo;
    public static Integer subtipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        sp_incidente= findViewById(R.id.combo_inicidente);
        sp_subtipo = findViewById(R.id.combo_subIncidente);
        btn_buscar = findViewById(R.id.btn_registrar);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_home = new Intent(ConsultarActivity.this, MainActivity.class);
                 tipo = validarTipo();
                 subtipo = validarSubTipo();
                i_home.putExtra("marker.tipo",tipo);
                i_home.putExtra("marker.subtipo",subtipo);


                startActivity(i_home);
            }
        });

        sp_incidente.setOnItemSelectedListener(this);


    }

    private Integer validarSubTipo() {
        String sp1= String.valueOf(sp_incidente.getSelectedItem());
        String sp2= String.valueOf(sp_subtipo.getSelectedItem());
        if(sp1.contentEquals("Bienestar")){
            if(sp2.contentEquals("Accidentes")){
                return 1;
            }else if(sp2.contentEquals("Basura en vía pública")){
                return 2;
            }else if (sp2.contentEquals("Accidentes")){
                return 3;
            }else if (sp2.contentEquals("Construcción Ilegal")){
                return 4;
            }else if (sp2.contentEquals("Problemas Viales")){
                return 5;
            }else if (sp2.contentEquals("Ruidos Molestos")){
                return 6;
            }else{
               return 0;
            }
        }
        return 0;
    }

    private Integer validarTipo() {
        String sp1= String.valueOf(sp_incidente.getSelectedItem());
        if(sp1.contentEquals("Bienestar")){
            return 1;
        }else if(sp1.contentEquals("Seguridad")){
            return 2;
        }else{
            return 0;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String sp1= String.valueOf(sp_incidente.getSelectedItem());

        if(sp1.contentEquals("Bienestar")) {
            List<String> list = new ArrayList<String>();
            list.add("Accidentes");
            list.add("Basura en vía pública");
            list.add("Construcción Ilegal");
            list.add("Problemas Viales");
            list.add("Ruidos Molestos");
            list.add("Todos");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            sp_subtipo.setAdapter(dataAdapter);
        }
        if(sp1.contentEquals("Seguridad")) {
            List<String> list = new ArrayList<String>();
            list.add("Acoso");
            list.add("Extraviado");
            list.add("Peleas");
            list.add("Robo");
            list.add("Vandalismo");
            list.add("Venta Drogas");
            list.add("Violencia Familiar");
            list.add("Todos");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp_subtipo.setAdapter(dataAdapter2);
        }

        if(sp1.contentEquals("Alerta Rápida")) {
            List<String> list = new ArrayList<String>();
            list.add("Alerta Rápida");

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp_subtipo.setAdapter(dataAdapter2);
        }

        if(sp1.contentEquals("Todos")) {
            List<String> list = new ArrayList<String>();
            list.add("Todos");

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp_subtipo.setAdapter(dataAdapter2);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
