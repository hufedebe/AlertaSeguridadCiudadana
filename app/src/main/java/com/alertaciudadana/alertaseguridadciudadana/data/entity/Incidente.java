package com.alertaciudadana.alertaseguridadciudadana.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Incidente {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("tipo")
    @Expose
    private String tipo;


    @SerializedName("subtipo")
    @Expose
    private Integer subTipo;

    @SerializedName("nombreIncidente")
    @Expose
    private String nombreIncidente;


    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("celular")
    @Expose
    private String celular;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("hora")
    @Expose
    private String hora;

    @SerializedName("latitud")
    @Expose
    private Double latitud;

    @SerializedName("longitud")
    @Expose
    private Double longitud;


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

    public Integer getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(Integer subTipo) {
        this.subTipo = subTipo;
    }

    public String getNombreIncidente() {
        return nombreIncidente;
    }

    public void setNombreIncidente(String nombreIncidente) {
        this.nombreIncidente = nombreIncidente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
