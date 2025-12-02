package com.iteso.proyectoFinal.ui;

import com.iteso.proyectoFinal.dao.SemestreDAO;
import com.iteso.proyectoFinal.modelos.Semestre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/*
 * Diálogo para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) de Semestres.
 * Esta clase extiende JDialog para crear una ventana modal de administración de semestres.
 * Permite gestionar los períodos académicos del sistema educativo.
 */
public class SemestreCRUDDialog extends JDialog
{
    private SemestreDAO dao = new SemestreDAO();
    private DefaultTableModel model;
    private GestorUI parent;

    /**
     * Constructor del diálogo CRUD de semestres.
     *
     * @param parent Ventana principal que crea este diálogo
     */
    public SemestreCRUDDialog(GestorUI parent)
    {
        super(parent, "CRUD - Semestres", true);
        this.parent = parent;
        setSize(500, 350);
        setLocationRelativeTo(parent);

        // ================================================
        // CONFIGURACIÓN DE LA TABLA
        // ================================================
        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Periodo"}, 0) {

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
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona un semestre"); return; }
            int id = (int) model.getValueAt(sel, 0);
            abrirFormulario(new Semestre(id, (String) model.getValueAt(sel,1), (String) model.getValueAt(sel,2)));
        });
        btnDel.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona un semestre"); return; }
            int id = (int) model.getValueAt(sel, 0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar semestre?") == JOptionPane.YES_OPTION) {
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
     * Abre un formulario para agregar o editar un semestre.
     *
     * @param s Semestre a editar, o null para agregar uno nuevo
     */
    private void abrirFormulario(Semestre s)
    {
        JTextField tfNombre = new JTextField(12);
        JTextField tfPeriodo = new JTextField(8);
        if (s != null) { tfNombre.setText(s.getNombre()); tfPeriodo.setText(s.getPeriodo()); }

        JPanel p = new JPanel(new GridLayout(0,2,6,6));
        p.add(new JLabel("Nombre:")); p.add(tfNombre);
        p.add(new JLabel("Periodo:")); p.add(tfPeriodo);

        int ok = JOptionPane.showConfirmDialog(this, p, s == null ? "Agregar Semestre" : "Editar Semestre", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            if (tfNombre.getText().isBlank() || tfPeriodo.getText().isBlank()) { JOptionPane.showMessageDialog(this, "Completa campos"); return; }
            if (s == null) {
                dao.agregar(new Semestre(0, tfNombre.getText(), tfPeriodo.getText()));
            } else {
                s.setNombre(tfNombre.getText()); s.setPeriodo(tfPeriodo.getText()); dao.actualizar(s);
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
        List<Semestre> list = dao.obtenerTodos();
        for (Semestre s : list) model.addRow(new Object[]{s.getId(), s.getNombre(), s.getPeriodo()});
        parent.recargarDatos();
    }
}
