package com.iteso.proyectoFinal.ui;

import com.iteso.proyectoFinal.dao.AlumnoDAO;
import com.iteso.proyectoFinal.dao.CalificacionDAO;
import com.iteso.proyectoFinal.dao.MateriaDAO;
import com.iteso.proyectoFinal.dao.SemestreDAO;
import com.iteso.proyectoFinal.modelos.Alumno;
import com.iteso.proyectoFinal.modelos.Calificacion;
import com.iteso.proyectoFinal.modelos.Materia;
import com.iteso.proyectoFinal.modelos.Semestre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CalificacionCRUDDialog extends JDialog {

    private CalificacionDAO dao = new CalificacionDAO();
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private MateriaDAO materiaDAO = new MateriaDAO();
    private SemestreDAO semestreDAO = new SemestreDAO();

    private DefaultTableModel model;
    private GestorUI parent;

    public CalificacionCRUDDialog(GestorUI parent) {
        super(parent, "CRUD - Calificaciones", true);
        this.parent = parent;
        setSize(800, 450);
        setLocationRelativeTo(parent);

        model = new DefaultTableModel(new String[]{"ID", "Expediente", "Alumno", "Materia", "Semestre", "Tipo", "Valor"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        JTable table = new JTable(model);
        table.removeColumn(table.getColumnModel().getColumn(0));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Agregar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDel = new JButton("Eliminar");

        btnAdd.addActionListener(e -> abrirFormulario(null));
        btnEdit.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona una calificación"); return; }
            int id = (int) model.getValueAt(sel, 0);
            Calificacion c = dao.obtenerPorId(id);
            abrirFormulario(c);
        });
        btnDel.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecciona una calificación"); return; }
            int id = (int) model.getValueAt(sel, 0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar calificación?") == JOptionPane.YES_OPTION) {
                dao.eliminar(id); recargar();
            }
        });

        top.add(btnAdd); top.add(btnEdit); top.add(btnDel);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        recargar();
    }

    private void abrirFormulario(Calificacion c) {
        List<Alumno> alumnos = alumnoDAO.obtenerTodos();
        List<Materia> materias = materiaDAO.obtenerTodos();
        List<Semestre> semestres = semestreDAO.obtenerTodos();

        JComboBox<Alumno> cbAlumno = new JComboBox<>();
        for (Alumno a : alumnos) cbAlumno.addItem(a);
        JComboBox<Materia> cbMateria = new JComboBox<>();
        for (Materia m : materias) cbMateria.addItem(m);
        JComboBox<Semestre> cbSem = new JComboBox<>();
        for (Semestre s : semestres) cbSem.addItem(s);
        JTextField tfTipo = new JTextField(10);
        JTextField tfValor = new JTextField(6);

        if (c != null) {
            // seleccionar items en combos
            for (int i = 0; i < cbAlumno.getItemCount(); i++) if (cbAlumno.getItemAt(i).getId() == c.getIdAlumno()) cbAlumno.setSelectedIndex(i);
            for (int i = 0; i < cbMateria.getItemCount(); i++) if (cbMateria.getItemAt(i).getId() == c.getIdMateria()) cbMateria.setSelectedIndex(i);
            for (int i = 0; i < cbSem.getItemCount(); i++) if (cbSem.getItemAt(i).getId() == c.getIdSemestre()) cbSem.setSelectedIndex(i);
            tfTipo.setText(c.getTipo()); tfValor.setText(String.valueOf(c.getValor()));
        }

        JPanel p = new JPanel(new GridLayout(0,2,6,6));
        p.add(new JLabel("Alumno:")); p.add(cbAlumno);
        p.add(new JLabel("Materia:")); p.add(cbMateria);
        p.add(new JLabel("Semestre:")); p.add(cbSem);
        p.add(new JLabel("Tipo:")); p.add(tfTipo);
        p.add(new JLabel("Valor:")); p.add(tfValor);

        int ok = JOptionPane.showConfirmDialog(this, p, c == null ? "Agregar Calificación" : "Editar Calificación", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            try {
                Alumno al = (Alumno) cbAlumno.getSelectedItem();
                Materia ma = (Materia) cbMateria.getSelectedItem();
                Semestre se = (Semestre) cbSem.getSelectedItem();
                double val = Double.parseDouble(tfValor.getText());

                if (c == null) {
                    Calificacion nuevo = new Calificacion(0, al.getId(), ma.getId(), se.getId(), val, tfTipo.getText());
                    dao.agregar(nuevo);
                } else {
                    c.setIdAlumno(al.getId()); c.setIdMateria(ma.getId()); c.setIdSemestre(se.getId());
                    c.setTipo(tfTipo.getText()); c.setValor(val);
                    dao.actualizar(c);
                }
                recargar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor inválido");
            }
        }
    }

    private void recargar()
    {
        model.setRowCount(0);
        // Para llenar la tabla con expedientes y demás usamos CalificacionExtendidaDAO a través del parent
        CalificacionCRUDFiller.fillModelFromParent(parent, model);
        parent.recargarDatos();
    }
}
