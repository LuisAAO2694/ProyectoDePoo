package com.iteso.proyectoFinal.ui;

import com.iteso.proyectoFinal.dao.CalificacionExtendidaDAO;
import com.iteso.proyectoFinal.modelos.CalificacionExtendida;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CalificacionCRUDFiller {
    public static void fillModelFromParent(GestorUI parent, DefaultTableModel model) {
        CalificacionExtendidaDAO dao = new CalificacionExtendidaDAO();
        List<CalificacionExtendida> datos = dao.obtenerTodas(null, null, null);
        for (CalificacionExtendida c : datos) {
            model.addRow(new Object[]{
                    c.getIdCalificacion(),
                    c.getExpediente(),
                    c.getNombre() + " " + c.getApellido(),
                    c.getCurso(),
                    c.getSemestre(),
                    c.getTipo(),
                    c.getValor()
            });
        }
    }
}
