package com.iteso.proyectoPoo.UI;

import com.iteso.proyectoPoo.Dao.CalificacionExtendidaDAO;
import com.iteso.proyectoPoo.Modelos.CalificacionExtendida;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestorUI extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private CalificacionExtendidaDAO dao = new CalificacionExtendidaDAO();

    public GestorUI() {
        setTitle("Gestor Escolar - Calificaciones");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // PANEL PRINCIPAL
        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        // MODELO DE LA TABLA
        modeloTabla = new DefaultTableModel(
                new String[]{
                        "Expediente", "Nombre", "Apellido",
                        "Semestre", "Periodo", "Curso",
                        "Tipo", "Valor"
                }, 0);

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);

        // Scroll
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);

        // Botón de refrescar
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarDatos());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRefrescar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarDatos();
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<CalificacionExtendida> datos = dao.obtenerCalificacionesExtendidas();

        for (CalificacionExtendida c : datos) {
            modeloTabla.addRow(new Object[]{
                    c.getExpediente(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getSemestre(),
                    c.getPeriodo(),
                    c.getCurso(),
                    c.getTipo(),
                    c.getValor()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestorUI().setVisible(true));
    }
}
