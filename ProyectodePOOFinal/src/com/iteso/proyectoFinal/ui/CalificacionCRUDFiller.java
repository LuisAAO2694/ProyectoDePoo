package com.iteso.proyectoFinal.ui;

import com.iteso.proyectoFinal.dao.CalificacionExtendidaDAO;
import com.iteso.proyectoFinal.modelos.CalificacionExtendida;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/*
 * Clase auxiliar para llenar modelos de tabla con datos de calificaciones extendidas.
 * Esta clase actúa como un "llenador" (filler) que obtiene datos combinados de múltiples tablas
 * y los presenta en un formato adecuado para mostrar en la interfaz gráfica.
 */
public class CalificacionCRUDFiller
{
    /**
     * Llena un modelo de tabla con todas las calificaciones extendidas.
     * Obtiene datos combinados de alumno, materia y semestre para mostrar información completa.
     *
     * @param parent Referencia a la ventana principal (no se usa directamente aquí,
     *               pero se mantiene para consistencia con la firma original)
     * @param model  Modelo de tabla a llenar con los datos de calificaciones
     */
    public static void fillModelFromParent(GestorUI parent, DefaultTableModel model)
    {
        // ================================================
        // OBTENER DATOS DESDE LA BASE DE DATOS
        // ================================================
        CalificacionExtendidaDAO dao = new CalificacionExtendidaDAO();
        List<CalificacionExtendida> datos = dao.obtenerTodas(null, null, null);

        // ================================================
        // PROCESAR Y AGREGAR DATOS AL MODELO DE TABLA
        // ================================================
        for (CalificacionExtendida c : datos)
        {
            model.addRow(new Object[]
                    {
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
