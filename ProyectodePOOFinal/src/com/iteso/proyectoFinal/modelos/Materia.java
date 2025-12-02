package com.iteso.proyectoFinal.modelos;

//Clase modelo que representa una Materia en el sistema.
public class Materia
{
    private int id;
    private String nombre;

    /**
     * Constructor sin parámetros (constructor por defecto).
     * Permite crear un objeto Alumno vacío y luego establecer sus valores con setters.
     */
    public Materia() {}

    /**
     * Constructor con todos los parámetros.
     * Permite crear un objeto Materia con todos sus valores inicializados.
     *
     * @param id     Identificador único de la materia
     * @param nombre Nombre de la materia
     */
    public Materia(int id, String nombre) { this.id = id; this.nombre = nombre; }

    // ================================================
    // GETTERS Y SETTERS
    // ================================================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() { return nombre; }
}
