package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alertaciudadana.alertaseguridadciudadana.R;
import com.alertaciudadana.alertaseguridadciudadana.view.model.IncidenteModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IncidenteActivity extends AppCompatActivity  {

    ImageButton btn_ubicacion, btn_camera;
    Button btn_registrar;


    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
    ImageView img_logo;
    String selectedImagePath;
    EditText editText_Incidente;
    EditText editText_Descripcion;
    EditText editText_Numero;
    EditText editText_Correo;
    EditText date;
    EditText editText_Hora;
    TextView txt_time;
    LinearLayout layout_correo, layout_numero;
    Bitmap imageBitmap;




    static final int REQUEST_IMAGE_CAPTURE = 1;


    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();


    SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
    String strDate = "Current Date : " + mdformat.format(c.getTime());

    final Integer hora_calendario = c.get(Calendar.HOUR_OF_DAY);
    final Integer minuto_calendario = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidente);
        final String incidente= getIntent().getStringExtra("INCIDENTE");
        final String latitud  = getIntent().getStringExtra("LATITUD");
        final String longitud = getIntent().getStringExtra("LONGITUD");
        final String tipo = getIntent().getStringExtra("TIPO");
        final Integer idSubtipo = getIntent().getIntExtra("IDSUBTIPO",0);

        //DATOS DE LA PANTALLA
        final String descripcion= getIntent().getStringExtra("DESCRIPCION");
        final String numero= getIntent().getStringExtra("NUMERO");
        final String correo= getIntent().getStringExtra("CORREO");
        final String fecha= getIntent().getStringExtra("FECHA");
        final String hora= getIntent().getStringExtra("HORA");





        btn_ubicacion = findViewById(R.id.btn_ubicacion);
        btn_camera = findViewById(R.id.btn_camara);
        btn_registrar = findViewById(R.id.btn_registrar);
        editText_Incidente=findViewById(R.id.editText_Incidente);
        editText_Descripcion=findViewById(R.id.editText_Descripcion);
        editText_Incidente=findViewById(R.id.editText_Incidente);
        editText_Numero=findViewById(R.id.editText_Numero);
        editText_Correo=findViewById(R.id.editText_Correo);
        editText_Hora=findViewById(R.id.editText_Hora);
        date = findViewById(R.id.date);



        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        date.addTextChangedListener(tw);
        txt_time= findViewById(R.id.txt_time);
        layout_correo = findViewById(R.id.layout_correo);
        layout_numero = findViewById(R.id.layout_numero);


        txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerHora();
            }
        });




        if(incidente.equals("otro")){

        }else{
            editText_Incidente.setText(incidente);
            editText_Incidente.setInputType(0);
        }

        if(tipo.equals("A")){
            btn_ubicacion.setVisibility(View.GONE);
            editText_Correo.setVisibility(View.GONE);
            layout_correo.setVisibility(View.GONE);
            editText_Numero.setVisibility(View.GONE);
            layout_numero.setVisibility(View.GONE);
            editText_Incidente.setText("ALERTA RÁPIDA");
            editText_Incidente.setInputType(0);
            txt_time.setText(hora_calendario.toString()+ DOS_PUNTOS + minuto_calendario.toString() );
            date.setText(strDate);
        }



        if(descripcion!=null){
            editText_Descripcion.setText(descripcion);

        }
        if(numero!=null){
            editText_Numero.setText(numero);
        }

        if(correo!=null){
            editText_Correo.setText(correo);
        }

        if(fecha!=null){
            date.setText(fecha);
        }
        if(hora !=null){
            txt_time.setText(hora);
        }








        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_ubicacion = new Intent(IncidenteActivity.this,UbicacionActivity.class);
                i_ubicacion.putExtra("INCIDENTE", incidente.toString());
                i_ubicacion.putExtra("TIPO",tipo);
                i_ubicacion.putExtra("DESCRIPCION",editText_Descripcion.getText().toString());
                i_ubicacion.putExtra("NUMERO",editText_Numero.getText().toString());
                i_ubicacion.putExtra("CORREO",editText_Correo.getText().toString());
                i_ubicacion.putExtra("FECHA",date.getText().toString());
                i_ubicacion.putExtra("HORA",txt_time.getText().toString());
                i_ubicacion.putExtra("IDSUBTIPO",idSubtipo);
                startActivity(i_ubicacion);

            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDialog();

            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(!tipo.equals("A")){

                if(TextUtils.isEmpty(editText_Descripcion.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Por favor Ingrese una descripción", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(editText_Numero.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Por favor Ingrese Número de Contacto", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(editText_Correo.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Por favor Ingrese Correo de Contacto", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(date.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Por favor Ingrese Una Fecha", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(txt_time.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Por favor La Hora del Incidente", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(latitud)) {
                    Toast.makeText(getApplicationContext(), "Ingrese Ubicación del Incidente", Toast.LENGTH_LONG).show();

                }else{

                    int index =  LoginActivity.listIncidentes.size()+1;
                    IncidenteModel incidenteModel = new IncidenteModel();
                    incidenteModel.setId(Integer.toString(index));
                    incidenteModel.setSubtipo(editText_Incidente.getText().toString());
                    incidenteModel.setDescripcion(editText_Descripcion.getText().toString());
                    incidenteModel.setNumero(editText_Numero.getText().toString());
                    incidenteModel.setCorreo(editText_Correo.getText().toString());
                    incidenteModel.setFecha(date.getText().toString());
                    incidenteModel.setHora(txt_time.getText().toString());
                    incidenteModel.setImg_incidente(imageBitmap);
                    incidenteModel.setId_subtipo(idSubtipo);

                    if(tipo.equals("A")){
                        incidenteModel.setLatitude(latitud);
                        incidenteModel.setLongitud(longitud);
                    }else{
                        incidenteModel.setLatitude(latitud);
                        incidenteModel.setLongitud(longitud);
                    }

                    incidenteModel.setTipo(tipo);
                    LoginActivity.listIncidentes.add(incidenteModel);
                    Intent i_principal = new Intent(IncidenteActivity.this,MainActivity.class);
                    i_principal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i_principal);
                }


            }else{
                int index =  LoginActivity.listIncidentes.size()+1;
                IncidenteModel incidenteModel = new IncidenteModel();
                incidenteModel.setId(Integer.toString(index));
                incidenteModel.setSubtipo(editText_Incidente.getText().toString());
                incidenteModel.setDescripcion(editText_Descripcion.getText().toString());
                incidenteModel.setNumero(editText_Numero.getText().toString());
                incidenteModel.setCorreo(editText_Correo.getText().toString());
                incidenteModel.setFecha(date.getText().toString());
                incidenteModel.setHora(txt_time.getText().toString());
                incidenteModel.setImg_incidente(imageBitmap);
                incidenteModel.setId_subtipo(idSubtipo);


                if(tipo.equals("A")){
                    incidenteModel.setLatitude(latitud);
                    incidenteModel.setLongitud(longitud);
                }else{
                    incidenteModel.setLatitude(latitud);
                    incidenteModel.setLongitud(longitud);
                }

                incidenteModel.setTipo(tipo);
                LoginActivity.listIncidentes.add(incidenteModel);

                Intent i_principal = new Intent(IncidenteActivity.this,MainActivity.class);
                i_principal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i_principal);


            }

            }
        });
    }

    private void obtenerHora() {

        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                txt_time.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora_calendario, minuto_calendario, false);

        recogerHora.show();

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        outState.putString("DESCRIPCION",editText_Descripcion.getText().toString());
        outState.putString("NUMERO",editText_Hora.getText().toString());
        outState.putString("CORREO",editText_Correo.getText().toString());
        outState.putString("FECHA",date.getText().toString());
        outState.putString("HORA",editText_Hora.getText().toString());

        outPersistentState.putString("DESCRIPCION",editText_Descripcion.getText().toString());
        outPersistentState.putString("NUMERO",editText_Hora.getText().toString());
        outPersistentState.putString("CORREO",editText_Correo.getText().toString());
        outPersistentState.putString("FECHA",date.getText().toString());
        outPersistentState.putString("HORA",txt_time.getText().toString());



        super.onSaveInstanceState(outState, outPersistentState);




    }




    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                IncidenteActivity.this);
        myAlertDialog.setTitle("Suba las imágenes del Incidente");
        myAlertDialog.setMessage("Elija una opción");

        myAlertDialog.setPositiveButton("Galería",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pictureActionIntent = null;

                        pictureActionIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(
                                pictureActionIntent,
                                GALLERY_PICTURE);

                    }
                });

        myAlertDialog.setNegativeButton("Camara",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {


                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }

                    }
                });
        myAlertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            btn_camera.setImageBitmap(imageBitmap);



        }
    }
    /*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {

                Toast.makeText(getBaseContext(),

                        "Error capturando la foto", Toast.LENGTH_LONG)

                        .show();

                return;

            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);



                img_logo.setImageBitmap(bitmap);
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

                if (selectedImagePath != null) {
                    //txt_image_path.setText(selectedImagePath);
                }

                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview image
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);



                img_logo.setImageBitmap(bitmap);

            } else {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

*/
    private void storeImageTosdCard(Bitmap processedBitmap) {
        try {
            // TODO Auto-generated method stub

            OutputStream output;
            // Find the SD Card path
            File filepath = Environment.getExternalStorageDirectory();
            // Create a new folder in SD Card
            File dir = new File(filepath.getAbsolutePath() + "/appName/");
            dir.mkdirs();

            String imge_name = "appName" + System.currentTimeMillis()
                    + ".jpg";
            // Create a name for the saved image
            File file = new File(dir, imge_name);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();

            }

            try {

                output = new FileOutputStream(file);

                // Compress into png format image from 0% - 100%
                processedBitmap
                        .compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();

                int file_size = Integer
                        .parseInt(String.valueOf(file.length() / 1024));
                System.out.println("size ===>>> " + file_size);
                System.out.println("file.length() ===>>> " + file.length());

                selectedImagePath = file.getAbsolutePath();



            }

            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
