package com.iteso.proyectoFinal.modelos;

public class Semestre
{
    private int id;
    private String nombre;
    private String periodo;

    public Semestre() {}
    public Semestre(int id, String nombre, String periodo) {
        this.id = id; this.nombre = nombre; this.periodo = periodo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    @Override
    public String toString() { return nombre + " - " + periodo; }
}
