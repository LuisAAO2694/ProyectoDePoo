package com.iteso.proyectoFinal.modelos;

public class CalificacionExtendida
{
    private String expediente;
    private String nombre;
    private String apellido;
    private String semestre;
    private String periodo;
    private String curso;
    private String tipo;
    private double valor;
    private int idCalificacion;

    public CalificacionExtendida(int idCalificacion, String expediente, String nombre, String apellido,
                                 String semestre, String periodo, String curso, String tipo, double valor) {
        this.idCalificacion = idCalificacion;
        this.expediente = expediente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.semestre = semestre;
        this.periodo = periodo;
        this.curso = curso;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getIdCalificacion() { return idCalificacion; }
    public String getExpediente() { return expediente; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getSemestre() { return semestre; }
    public String getPeriodo() { return periodo; }
    public String getCurso() { return curso; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
}
