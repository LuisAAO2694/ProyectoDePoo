package com.iteso.proyectoFinal.modelos;

public class Calificacion
{
    private int id;
    private int idAlumno;
    private int idMateria;
    private int idSemestre;
    private double valor;
    private String tipo;

    public Calificacion() {}

    public Calificacion(int id, int idAlumno, int idMateria, int idSemestre, double valor, String tipo) {
        this.id = id; this.idAlumno = idAlumno; this.idMateria = idMateria; this.idSemestre = idSemestre; this.valor = valor; this.tipo = tipo;
    }

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
