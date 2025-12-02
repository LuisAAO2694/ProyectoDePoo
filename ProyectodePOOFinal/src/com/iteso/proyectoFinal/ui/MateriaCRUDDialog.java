package com.iteso.proyectoFinal.ui;

import com.iteso.proyectoFinal.dao.MateriaDAO;
import com.iteso.proyectoFinal.modelos.Materia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/*
 * Diálogo para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) de Materias.
 * Esta clase extiende JDialog para crear una ventana modal de administración de materias.
 * Permite gestionar los cursos/asignaturas del sistema educativo.
 */
public class MateriaCRUDDialog extends JDialog
{
    private MateriaDAO dao = new MateriaDAO();
    private DefaultTableModel model;
    private GestorUI parent;

    /**
     * Constructor del diálogo CRUD de materias.
     *
     * @param parent Ventana principal que crea este diálogo
     */
    public MateriaCRUDDialog(GestorUI parent)
    {
        super(parent, "CRUD - Materias", true);
        this.parent = parent;
        setSize(450, 350);
        setLocationRelativeTo(parent);

        // ================================================
        // CONFIGURACIÓN DE LA TABLA
        // ================================================
        model = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
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

        JTable table = new JTable(model);
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
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona una materia"); return; }
            int id = (int) model.getValueAt(sel, 0);
            abrirFormulario(new Materia(id, (String) model.getValueAt(sel,1)));
        });
        btnDel.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona una materia"); return; }
            int id = (int) model.getValueAt(sel, 0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar materia?") == JOptionPane.YES_OPTION) {
                dao.eliminar(id);
                recargar();
            }
        });

        top.add(btnAdd); top.add(btnEdit); top.add(btnDel);

        // ================================================
        // ORGANIZACIÓN DEL LAYOUT
        // ================================================
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        recargar();
    }

    /**
     * Abre un formulario para agregar o editar una materia.
     *
     * @param m Materia a editar, o null para agregar una nueva
     */
    private void abrirFormulario(Materia m)
    {
        JTextField tfNombre = new JTextField(20);
        if (m != null) tfNombre.setText(m.getNombre());
        int ok = JOptionPane.showConfirmDialog(this, tfNombre, m == null ? "Agregar Materia" : "Editar Materia", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            if (tfNombre.getText().isBlank()) { JOptionPane.showMessageDialog(this, "Nombre requerido"); return; }
            if (m == null) {
                dao.agregar(new Materia(0, tfNombre.getText()));
            } else {
                m.setNombre(tfNombre.getText()); dao.actualizar(m);
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
        List<Materia> list = dao.obtenerTodos();
        for (Materia m : list) model.addRow(new Object[]{m.getId(), m.getNombre()});
        parent.recargarDatos();
    }
}
