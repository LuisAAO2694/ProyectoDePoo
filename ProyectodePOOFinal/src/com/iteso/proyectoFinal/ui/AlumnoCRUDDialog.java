package com.iteso.proyectoFinal.ui;

import com.iteso.proyectoFinal.dao.AlumnoDAO;
import com.iteso.proyectoFinal.modelos.Alumno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/*
 * Diálogo para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) de Alumnos.
 * Esta clase extiende JDialog para crear una ventana modal de administración de alumnos.
 */
public class AlumnoCRUDDialog extends JDialog
{
    private JTable table;
    private DefaultTableModel model;
    private AlumnoDAO dao = new AlumnoDAO();
    private GestorUI parent;

    /**
     * Constructor del diálogo CRUD de alumnos.
     *
     * @param parent Ventana principal que crea este diálogo
     */
    public AlumnoCRUDDialog(GestorUI parent)
    {
        super(parent, "CRUD - Alumnos", true);
        this.parent = parent;
        setSize(600, 400);
        setLocationRelativeTo(parent);

        // ================================================
        // CONFIGURACIÓN DE LA TABLA
        // ================================================
        model = new DefaultTableModel(new String[]{"ID", "Expediente", "Nombre", "Apellido"}, 0) {

            /**
             * Sobrescribe para hacer las celdas no editables directamente.
             * La edición se hará mediante formularios separados.
             *
             * @param r Índice de fila
             * @param c Índice de columna
             * @return false para deshabilitar edición directa en celdas
             */
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.removeColumn(table.getColumnModel().getColumn(0));

        // ================================================
        // PANEL SUPERIOR CON BOTONES
        // ================================================

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Agregar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDel = new JButton("Eliminar");

        // ================================================
        // CONFIGURACIÓN DE EVENTOS DE BOTONES
        // ================================================
        btnAdd.addActionListener(e -> abrirFormulario(null));
        btnEdit.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona un alumno"); return; }
            int id = (int) model.getValueAt(sel, 0);
            abrirFormulario(dao.obtenerPorId(id));
        });
        btnDel.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona un alumno"); return; }
            int id = (int) model.getValueAt(sel, 0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar alumno?") == JOptionPane.YES_OPTION) {
                dao.eliminar(id);
                recargar();
            }
        });

        // ================================================
        // ORGANIZACIÓN DEL LAYOUT
        // ================================================
        top.add(btnAdd); top.add(btnEdit); top.add(btnDel);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        recargar();
    }

    /**
     * Abre un formulario para agregar o editar un alumno.
     *
     * @param a Alumno a editar, o null para agregar uno nuevo
     */
    private void abrirFormulario(Alumno a)
    {
        JTextField tfExp = new JTextField(10);
        JTextField tfNom = new JTextField(12);
        JTextField tfApe = new JTextField(12);
        if (a != null) { tfExp.setText(a.getExpediente()); tfNom.setText(a.getNombre()); tfApe.setText(a.getApellido()); }

        JPanel p = new JPanel(new GridLayout(0,2,6,6));
        p.add(new JLabel("Expediente:")); p.add(tfExp);
        p.add(new JLabel("Nombre:")); p.add(tfNom);
        p.add(new JLabel("Apellido:")); p.add(tfApe);

        int ok = JOptionPane.showConfirmDialog(this, p, a == null ? "Agregar" : "Editar", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            if (tfNom.getText().isBlank() || tfApe.getText().isBlank() || tfExp.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos");
                return;
            }
            if (a == null) {
                Alumno nuevo = new Alumno(0, tfNom.getText(), tfApe.getText(), tfExp.getText());
                dao.agregar(nuevo);
            } else {
                a.setExpediente(tfExp.getText()); a.setNombre(tfNom.getText()); a.setApellido(tfApe.getText());
                dao.actualizar(a);
            }
            recargar();
        }
    }

    /**
     * Recarga los datos de la tabla desde la base de datos.
     * También notifica a la ventana principal para actualizar otras vistas.
     */
    private void recargar()
    {
        model.setRowCount(0);
        List<Alumno> list = dao.obtenerTodos();
        for (Alumno a : list) model.addRow(new Object[]{a.getId(), a.getExpediente(), a.getNombre(), a.getApellido()});
        parent.recargarDatos();
    }
}
