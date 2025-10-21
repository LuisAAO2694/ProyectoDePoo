package com.iteso.proyectoPoo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Gestor extends JFrame
{
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Campos editables
    private JTextField txtExpediente, txtNombre;
    private JComboBox<String> cbSemestre, cbMateria, cbTipo;
    private JSpinner spPeriodo, spValor;
    private JButton btnGuardar, btnActualizar, btnEliminar;

    public Gestor() {
        setTitle("Gestor Escolar");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== Tabla de solo lectura =====
        String[] columnas = {"Expediente", "Nombre", "Semestre", "Periodo", "Materia", "Tipo", "Valor"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabla solo lectura
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);

        // ===== Panel de edición =====
        JPanel panelEdicion = new JPanel(new GridLayout(2, 7, 5, 5));
        txtExpediente = new JTextField();
        txtNombre = new JTextField();
        cbSemestre = new JComboBox<>(new String[]{"Enero–Mayo", "Agosto–Diciembre"});
        spPeriodo = new JSpinner(new SpinnerNumberModel(2025, 2000, 2100, 1));
        cbMateria = new JComboBox<>(new String[]{"Programación", "Matemáticas", "Bases de Datos"});
        cbTipo = new JComboBox<>(new String[]{"EXAMEN", "TAREA", "PROYECTO", "FINAL"});
        spValor = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.5));

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelEdicion.add(txtExpediente);
        panelEdicion.add(txtNombre);
        panelEdicion.add(cbSemestre);
        panelEdicion.add(spPeriodo);
        panelEdicion.add(cbMateria);
        panelEdicion.add(cbTipo);
        panelEdicion.add(spValor);
        panelEdicion.add(btnGuardar);
        panelEdicion.add(btnActualizar);
        panelEdicion.add(btnEliminar);

        // ===== Layout principal =====
        setLayout(new BorderLayout());
        add(scrollTabla, BorderLayout.CENTER);
        add(panelEdicion, BorderLayout.SOUTH);

        agregarDatosEjemplo();
    }

    private void agregarDatosEjemplo() {
        modeloTabla.addRow(new Object[]{"A001", "Luis Arriaga", "Enero–Mayo", 2025, "Programación", "EXAMEN", 95.0});
        modeloTabla.addRow(new Object[]{"A001", "Luis Arriaga", "Enero–Mayo", 2025, "Matemáticas", "TAREA", 88.5});
        modeloTabla.addRow(new Object[]{"A002", "Ana Torres", "Agosto–Diciembre", 2025, "Bases de Datos", "FINAL", 78.0});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gestor().setVisible(true));
    }
}
