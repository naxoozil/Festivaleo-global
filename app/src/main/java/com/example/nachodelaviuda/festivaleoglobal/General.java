package com.example.nachodelaviuda.festivaleoglobal;

public class General {
    private String titulo;
    private String descripcion;

    public General(){}

    public General(String titulo, String descripcion){
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
