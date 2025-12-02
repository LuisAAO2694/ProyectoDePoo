package com.iteso.proyectoFinal.modelos;

/**
 * Clase modelo que representa a un Alumno en el sistema.
 * Esta es una clase POJO (Plain Old Java Object) que encapsula los datos de un alumno.
 * Sigue el patrón JavaBean con constructor vacío, getters y setters.
 *
 * Pojo es una clase Java simple que no depende de ningún framework ni
 * librería específica, cuyo propósito principal es modelar datos de manera clara y sencilla.
 */
public class Alumno
{
    private int id;
    private String nombre;
    private String apellido;
    private String expediente;

    /**
     * Constructor sin parámetros (constructor por defecto).
     * Permite crear un objeto Alumno vacío y luego establecer sus valores con setters.
     */
    public Alumno() {}

    /**
     * Constructor con todos los parámetros.
     * Permite crear un objeto Alumno con todos sus valores inicializados.
     *
     * @param id        Identificador único en la base de datos
     * @param nombre    Nombre(s) del alumno
     * @param apellido  Apellido(s) del alumno
     * @param expediente Número de expediente/matrícula
     */
    public Alumno(int id, String nombre, String apellido, String expediente)
    {
        this.id = id; this.nombre = nombre; this.apellido = apellido; this.expediente = expediente;
    }

    // ================================================
    // GETTERS Y SETTERS
    // ================================================
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }

    /**
     * Devuelve una representación en String del objeto Alumno.
     * Útil para mostrar información del alumno en interfaces de usuario, logs, etc.
     *
     * @return String con formato: "expediente - nombre apellido"
     *         Ejemplo: "A123456 - Juan Pérez"
     */
    @Override
    public String toString()
    {
        return expediente + " - " + nombre + " " + apellido;
    }
}
