package com.example.nachodelaviuda.festivaleoglobal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElementoLista {

    private String nombre;
    private String lugar;
    private String imagenId;
    private int rate;
    private String descripcion;
    private float latitud;
    private float longitud;
    private String correoCreador;


    public ElementoLista(){
    }
    public ElementoLista(String nombre, String lugar, String imagenId, int rate,String descripcion, float latitud, float longitud,String correoCreador) {
        this.nombre         = nombre;
        this.lugar          = lugar;
        this.imagenId       = imagenId;
        this.rate           = rate;
        this.descripcion    = descripcion;
        this.latitud        = latitud;
        this.longitud       = longitud;
        this.correoCreador  = correoCreador;


    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nombre", nombre);
        result.put("lugar", lugar);
        result.put("imagenId", imagenId);
        result.put("rate", rate);
        result.put("descripcion", descripcion);
        result.put("latitud", latitud);
        result.put("longitud", longitud);
        result.put("correoCreador", correoCreador);
        return result;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getLatitud() { return latitud; }

    public void setLatitud(float latitud) { this.latitud = latitud; }

    public float getLongitud() { return longitud; }

    public void setLongitud(float longitud) { this.longitud = longitud; }

    public String getCorreoCreador() {
        return correoCreador;
    }
}
