package com.alertaciudadana.alertaseguridadciudadana.view.model;

import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;

public class IncidenteModel {
    private String id;
    private String tipo;
    private String subtipo;
    private Integer id_subtipo;
    private String descripcion;
    private String numero;
    private String correo;
    private String fecha;
    private String hora;
    private String latitude;
    private String longitud;
    //private ImageButton  img_incidente;
    private Bitmap img_incidente;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }




    public Integer getId_subtipo() {
        return id_subtipo;
    }

    public void setId_subtipo(Integer id_subtipo) {
        this.id_subtipo = id_subtipo;
    }


    public Bitmap getImg_incidente() {
        return img_incidente;
    }

    public void setImg_incidente(Bitmap img_incidente) {
        this.img_incidente = img_incidente;
    }
}
