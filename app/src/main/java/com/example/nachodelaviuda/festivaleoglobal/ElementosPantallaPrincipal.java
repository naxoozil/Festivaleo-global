package com.example.nachodelaviuda.festivaleoglobal;

public class ElementosPantallaPrincipal {
    private String nombre;
    private String lugar;
    private String imagenId;

    public ElementosPantallaPrincipal(String nombre, String lugar, String imagenId) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.imagenId = imagenId;
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
}
