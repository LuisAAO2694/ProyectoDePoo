package com.iteso.proyectoPoo;

import javax.swing.table.DefaultTableModel;

/**
 * Clase de lógica del sistema escolar.
 * Se encarga de manejar los registros de alumnos en la tabla.
 */
public class Gestor {

    private DefaultTableModel modeloTabla;

    // Constructor que recibe el modelo de la tabla desde la interfaz
    public Gestor(DefaultTableModel modeloTabla) {
        this.modeloTabla = modeloTabla;
    }

    // Agrega un nuevo registro a la tabla
    public void agregarRegistro(String expediente, String nombre, String semestre,
                                int periodo, String materia, String tipo, double valor) {
        modeloTabla.addRow(new Object[]{expediente, nombre, semestre, periodo, materia, tipo, valor});
    }

    // Elimina un registro seleccionado
    public void eliminarRegistro(int fila) {
        if (fila >= 0) {
            modeloTabla.removeRow(fila);
        }
    }

    // Actualiza un registro existente
    public void actualizarRegistro(int fila, String expediente, String nombre, String semestre,
                                   int periodo, String materia, String tipo, double valor) {
        if (fila >= 0) {
            modeloTabla.setValueAt(expediente, fila, 0);
            modeloTabla.setValueAt(nombre, fila, 1);
            modeloTabla.setValueAt(semestre, fila, 2);
            modeloTabla.setValueAt(periodo, fila, 3);
            modeloTabla.setValueAt(materia, fila, 4);
            modeloTabla.setValueAt(tipo, fila, 5);
            modeloTabla.setValueAt(valor, fila, 6);
        }
    }
}
// Hola mi nombre es ?