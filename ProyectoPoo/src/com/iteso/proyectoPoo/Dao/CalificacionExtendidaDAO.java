package com.iteso.proyectoPoo.Dao;

import com.iteso.proyectoPoo.conexionBD.ConexionDB;
import com.iteso.proyectoPoo.Modelos.CalificacionExtendida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalificacionExtendidaDAO
{
    public List<CalificacionExtendida> obtenerCalificacionesExtendidas() {
        List<CalificacionExtendida> lista = new ArrayList<>();

        String sql = """
                SELECT alumno.expediente,
                       alumno.nombre,
                       alumno.apellido,
                       semestre.nombre AS semestre,
                       semestre.periodo,
                       materia.nombre AS curso,
                       calificacion.tipo,
                       calificacion.valorcalificacion
                FROM calificacion
                INNER JOIN alumno ON calificacion.id_alumno = alumno.id_alumno
                INNER JOIN materia ON calificacion.id_materia = materia.id_materia
                INNER JOIN semestre ON calificacion.id_semestre = semestre.id_semestre
                ORDER BY alumno.expediente;
                """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new CalificacionExtendida(
                        rs.getString("expediente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("semestre"),
                        rs.getString("periodo"),
                        rs.getString("curso"),
                        rs.getString("tipo"),
                        rs.getDouble("valorcalificacion")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
