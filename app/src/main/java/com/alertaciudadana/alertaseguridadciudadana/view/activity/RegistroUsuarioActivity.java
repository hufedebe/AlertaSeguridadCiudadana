package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.RegistroPost;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.RegistroResponse;
import com.alertaciudadana.alertaseguridadciudadana.data.net.ApiAdapter;
import com.github.mikephil.charting.utils.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alertaciudadana.alertaseguridadciudadana.utils.ActivityUtils.hideSoftKeyboard;

public class RegistroUsuarioActivity extends AppCompatActivity {

    EditText nombre, apellido, celular, email, password, direccion;
    Button btn_registro;
    ImageView back_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        setupUI(findViewById(R.id.registro_layout));


        nombre = findViewById(R.id.editText_nombre);
        apellido = findViewById(R.id.editText_apellido);
        celular = findViewById(R.id.editText_celular);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        direccion = findViewById(R.id.editText_direccion);
        btn_registro= findViewById(R.id.btn_registro);
        back_arrow = findViewById(R.id.back_arrow);



        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegistroUsuarioActivity.this,LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
            }
        });



        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nombre.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Ingrese su nombre", Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty(apellido.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Ingrese su apellido", Toast.LENGTH_LONG).show();

                }

                else if(TextUtils.isEmpty(celular.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Ingrese su número de celular", Toast.LENGTH_LONG).show();

                }

                else if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Ingrese su correo", Toast.LENGTH_LONG).show();

                }

                else if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Ingrese su contraseña", Toast.LENGTH_LONG).show();

                }else {
                    RegistroPost registroPost = new RegistroPost();
                    registroPost.setNombre(nombre.getText().toString());
                    registroPost.setApellido(apellido.getText().toString());
                    registroPost.setCelular(Integer.valueOf(celular.getText().toString()));
                    registroPost.setEmail(email.getText().toString());
                    registroPost.setPassword(password.getText().toString());
                    if( !TextUtils.isEmpty(direccion.getText().toString())){
                        registroPost.setDireccion(direccion.getText().toString());
                    }

                    Call<RegistroResponse> call = ApiAdapter.getApiService().postRegistro(registroPost);
                    call.enqueue(new RegistrarPersonaCallback());



                }





            }
        });





    }


    class RegistrarPersonaCallback implements Callback<RegistroResponse> {

        @Override
        public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
            if(response.isSuccessful()){
                RegistroResponse registroResponse = response.body();
                if (!TextUtils.isEmpty(registroResponse.getId())){

                    Toast.makeText(getApplicationContext(),"Se registró correctamente ", Toast.LENGTH_SHORT).show();
                    Intent register = new Intent(RegistroUsuarioActivity.this,PrincipalActivity.class);
                    register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(register);

                }else{
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
        public void onFailure(Call<RegistroResponse> call, Throwable t) {

        }
    }



    public void setupUI(View view) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(RegistroUsuarioActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
