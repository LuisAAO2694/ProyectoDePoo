package com.iteso.proyectoFinal.modelos;

//Clase modelo que representa un Semestre en el sistema.
public class Semestre
{
    private int id;
    private String nombre;
    private String periodo;

    /**
     * Constructor sin parámetros (constructor por defecto).
     */
    public Semestre() {}

    /**
     * Constructor con todos los parámetros.
     * Permite crear un objeto Semestre con todos sus valores inicializados.
     *
     * @param id      Identificador único del semestre
     * @param nombre  Nombre descriptivo del semestre
     * @param periodo Identificador único del período
     */
    public Semestre(int id, String nombre, String periodo) {
        this.id = id; this.nombre = nombre; this.periodo = periodo;
    }

    // ================================================
    // GETTERS Y SETTERS
    // ================================================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    @Override
    public String toString() { return nombre + " - " + periodo; }
}
