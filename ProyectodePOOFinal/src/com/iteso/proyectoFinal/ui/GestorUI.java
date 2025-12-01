package com.iteso.proyectoFinal.ui;

import javax.swing.*;

import com.iteso.proyectoFinal.dao.CalificacionExtendidaDAO;
import com.iteso.proyectoFinal.dao.MateriaDAO;
import com.iteso.proyectoFinal.dao.SemestreDAO;
import com.iteso.proyectoFinal.modelos.CalificacionExtendida;
import com.iteso.proyectoFinal.modelos.Materia;
import com.iteso.proyectoFinal.modelos.Semestre;
import com.iteso.proyectoFinal.utils.ExportUtil;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class GestorUI extends JFrame
{
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField tfFiltroExpediente;
    private JComboBox<Semestre> cbSemestreFiltro;
    private JComboBox<Materia> cbMateriaFiltro;

    private CalificacionExtendidaDAO calDAO = new CalificacionExtendidaDAO();
    private SemestreDAO semestreDAO = new SemestreDAO();
    private MateriaDAO materiaDAO = new MateriaDAO();

    public GestorUI() {
        setTitle("Gestor Escolar - Dashboard");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===============================================================
        // TOP PANEL (FILTROS + EXPORTAR CSV)
        // ===============================================================
        JPanel top = new JPanel(new BorderLayout(8,8));
        top.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        add(top, BorderLayout.NORTH);

        // -------- Filtros ----------
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        tfFiltroExpediente = new JTextField(12);
        cbSemestreFiltro = new JComboBox<>();
        cbMateriaFiltro = new JComboBox<>();

        cbSemestreFiltro.addItem(null);
        for (Semestre s : semestreDAO.obtenerTodos()) cbSemestreFiltro.addItem(s);

        cbMateriaFiltro.addItem(null);
        for (Materia m : materiaDAO.obtenerTodos()) cbMateriaFiltro.addItem(m);

        filtros.add(new JLabel("Expediente:"));
        filtros.add(tfFiltroExpediente);
        filtros.add(new JLabel("Semestre:"));
        filtros.add(cbSemestreFiltro);
        filtros.add(new JLabel("Materia:"));
        filtros.add(cbMateriaFiltro);

        JButton btnAplicar = new JButton("Aplicar filtros");
        btnAplicar.addActionListener(e -> cargarTabla());
        filtros.add(btnAplicar);

        top.add(filtros, BorderLayout.WEST);

        // -------- Acciones (Exportar) ----------
        JPanel accionesArriba = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));

        JButton btnExportCSV = new JButton("Exportar a CSV");
        btnExportCSV.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                if (!f.getName().toLowerCase().endsWith(".csv"))
                    f = new File(f.getAbsolutePath() + ".csv");

                boolean ok = ExportUtil.exportTableToCSV(tabla, f);
                JOptionPane.showMessageDialog(this, ok ?
                        "Exportado a CSV: " + f.getAbsolutePath() : "Error exportando CSV");
            }
        });

        accionesArriba.add(btnExportCSV);
        top.add(accionesArriba, BorderLayout.EAST);

        // ===============================================================
        // TABLA PRINCIPAL
        // ===============================================================
        modelo = new DefaultTableModel(new String[]{
                "ID", "Expediente", "Nombre", "Apellido",
                "Semestre", "Periodo", "Materia", "Tipo", "Valor"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modelo);
        tabla.removeColumn(tabla.getColumnModel().getColumn(0)); // Ocultar ID
        tabla.setRowHeight(26);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ===============================================================
        // PANEL INFERIOR (CRUD)
        // ===============================================================
        JPanel panelInferior = new JPanel(new GridLayout(1, 5, 10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnAlumnos = new JButton("Alumnos");
        btnAlumnos.addActionListener(e -> new AlumnoCRUDDialog(this).setVisible(true));

        JButton btnMaterias = new JButton("Materias");
        btnMaterias.addActionListener(e -> new MateriaCRUDDialog(this).setVisible(true));

        JButton btnSemestres = new JButton("Semestres");
        btnSemestres.addActionListener(e -> new SemestreCRUDDialog(this).setVisible(true));

        JButton btnCalificaciones = new JButton("Calificaciones");
        btnCalificaciones.addActionListener(e -> new CalificacionCRUDDialog(this).setVisible(true));

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarTabla());

        panelInferior.add(btnAlumnos);
        panelInferior.add(btnMaterias);
        panelInferior.add(btnSemestres);
        panelInferior.add(btnCalificaciones);
        panelInferior.add(btnRefrescar);

        add(panelInferior, BorderLayout.SOUTH);

        // ===============================================================
        // ESTILO Y CARGA INICIAL
        // ===============================================================
        aplicarEstiloMaterial();
        cargarTabla();
    }

    private void aplicarEstiloMaterial() {
        tabla.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        tabla.setFont(new Font("Dialog", Font.PLAIN, 12));
    }

    public void cargarTabla() {
        modelo.setRowCount(0);

        String filtroExp = tfFiltroExpediente.getText();
        Integer idSem = cbSemestreFiltro.getSelectedIndex() <= 0 ?
                null : ((Semestre)cbSemestreFiltro.getSelectedItem()).getId();
        Integer idMat = cbMateriaFiltro.getSelectedIndex() <= 0 ?
                null : ((Materia)cbMateriaFiltro.getSelectedItem()).getId();

        List<CalificacionExtendida> datos = calDAO.obtenerTodas(filtroExp, idSem, idMat);

        for (CalificacionExtendida c : datos) {
            modelo.addRow(new Object[]{
                    c.getIdCalificacion(),
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

    public void recargarDatos() { cargarTabla(); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestorUI().setVisible(true));
    }
}
