package com.alertaciudadana.alertaseguridadciudadana.data.entity;

import java.nio.Buffer;

public class IncidentePost {

    private String tipo;
    private Integer subTipo;
    private String nombreIncidente;
    private String userId;
    private String descripcion;
    private String celular;
    private String email;
    private String fecha;
    private String hora;
    private Buffer foto;
    private Double latitud;
    private Double longitud;


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Buffer getFoto() {
        return foto;
    }

    public void setFoto(Buffer foto) {
        this.foto = foto;
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

    public String getNombreIncidente() {
        return nombreIncidente;
    }

    public void setNombreIncidente(String nombreIncidente) {
        this.nombreIncidente = nombreIncidente;
    }
}
