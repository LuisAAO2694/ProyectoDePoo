package com.iteso.proyectoPoo.Modelos;

public class Alumno
{

    private int id;
    private String nombre;
    private String apellido;
    private String expediente;

    public Alumno() {}

    public Alumno(int id, String nombre, String apellido, String expediente) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.expediente = expediente;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }
}
