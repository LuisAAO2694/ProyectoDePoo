package com.iteso.proyectoFinal.modelos;

//Clase modelo que representa una Calificación en el sistema
public class Calificacion
{
    private int id;
    private int idAlumno;
    private int idMateria;
    private int idSemestre;
    private double valor;
    private String tipo;

    /**
     * Constructor sin parámetros (constructor por defecto).
     * crear un objeto de una clase sin inicializar sus atributos
     * con valores específicos, permitiendo asignar los datos posteriormente.
     */
    public Calificacion() {}

    /**
     * Constructor con todos los parámetros.
     * Permite crear un objeto Calificacion con todos sus valores inicializados.
     *
     * @param id          Identificador único de la calificación
     * @param idAlumno    ID del alumno evaluado
     * @param idMateria   ID de la materia evaluada
     * @param idSemestre  ID del semestre de evaluación
     * @param valor       Valor numérico de la calificación
     * @param tipo        Tipo de evaluación
     */
    public Calificacion(int id, int idAlumno, int idMateria, int idSemestre, double valor, String tipo) {
        this.id = id; this.idAlumno = idAlumno; this.idMateria = idMateria; this.idSemestre = idSemestre; this.valor = valor; this.tipo = tipo;
    }

    // ================================================
    // GETTERS Y SETTERS
    // ================================================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }

    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }

    public int getIdSemestre() { return idSemestre; }
    public void setIdSemestre(int idSemestre) { this.idSemestre = idSemestre; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
