package com.iteso.proyectoFinal.dao;

import com.iteso.proyectoFinal.conexionBD.conexionBD;
import com.iteso.proyectoFinal.modelos.CalificacionExtendida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Realiza JOINs entre múltiples tablas para obtener información detallada.
 * Implementa filtros dinámicos para búsquedas personalizadas.
 */
public class CalificacionExtendidaDAO
{
    /**
     * Obtiene todas las calificaciones con información extendida (JOINs).
     * Realiza búsquedas con filtros opcionales para refinar resultados.
     *
     * @param filtroExpediente   Filtro por expediente del alumno (puede contener texto parcial)
     * @param filtroSemestreId   Filtro por ID del semestre
     * @param filtroMateriaId    Filtro por ID de la materia
     * @return Lista de objetos CalificacionExtendida con información detallada
     */
    public List<CalificacionExtendida> obtenerTodas(String filtroExpediente, Integer filtroSemestreId, Integer filtroMateriaId) {
        List<CalificacionExtendida> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
                SELECT c.id_calificacion,
                       a.expediente,
                       a.nombre AS nombre_alumno,
                       a.apellido,
                       s.nombre AS semestre,
                       s.periodo,
                       m.nombre AS curso,
                       c.tipo,
                       c.valorcalificacion
                FROM calificacion c
                JOIN alumno a ON c.id_alumno = a.id_alumno
                JOIN materia m ON c.id_materia = m.id_materia
                JOIN semestre s ON c.id_semestre = s.id_semestre
                WHERE 1=1
                """);

        if (filtroExpediente != null && !filtroExpediente.isBlank())
        {
            sql.append(" AND a.expediente LIKE ?");
        }
        if (filtroSemestreId != null) sql.append(" AND s.id_semestre = ?");
        if (filtroMateriaId != null) sql.append(" AND m.id_materia = ?");

        sql.append(" ORDER BY a.expediente, m.nombre");

        try (Connection conn = conexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString()))
        {

            int idx = 1;
            if (filtroExpediente != null && !filtroExpediente.isBlank()) {
                ps.setString(idx++, "%" + filtroExpediente + "%");
            }
            if (filtroSemestreId != null) ps.setInt(idx++, filtroSemestreId);
            if (filtroMateriaId != null) ps.setInt(idx++, filtroMateriaId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CalificacionExtendida(
                        rs.getInt("id_calificacion"),
                        rs.getString("expediente"),
                        rs.getString("nombre_alumno"),
                        rs.getString("apellido"),
                        rs.getString("semestre"),
                        rs.getString("periodo"),
                        rs.getString("curso"),
                        rs.getString("tipo"),
                        rs.getDouble("valorcalificacion")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
