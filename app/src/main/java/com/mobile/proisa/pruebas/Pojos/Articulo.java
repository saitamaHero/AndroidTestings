package com.mobile.proisa.pruebas.Pojos;

import java.util.Locale;

public class Articulo {
    private String codigo;
    private String nombre;
    private double precio;
    private double costo;
    private double cantidad;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int result = 17;

        result =  31 * result + codigo.hashCode();
        result =  31 * result + nombre.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Articulo articulo = (Articulo)obj;

        if(articulo == null) return false;

        return this.codigo.equals(articulo.getCodigo()) &&
               this.nombre.equals(articulo.getNombre());
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Articulo[%s, %s]", getCodigo(), getNombre());
    }
}
